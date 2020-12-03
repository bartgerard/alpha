package be.gerard.robot.service;

import be.gerard.robot.model.Camera;
import be.gerard.robot.model.KeyFrame;
import be.gerard.robot.model.Line2d;
import be.gerard.robot.model.Point2d;
import be.gerard.robot.model.Scene;
import be.gerard.robot.model.Segment;
import be.gerard.robot.model.Timelapse;
import be.gerard.robot.model.Vector;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class DirectorService {

    public static final int SIXTY_DEGREE = 60;
    private final RobotService robotService;
    private final CameraService cameraService;

    private static Stream<Segment> toSegments(
            final KeyFrame current,
            final KeyFrame next
    ) {
        final var vector = current.getPoint().min(next.getPoint());
        final var angleBetweenPoints = vector.angle2d().toSmallestAngle();
        final var angleBetweenVectors = Vector.angle2d(
                current.getDirection(),
                next.getDirection()
        )
                .toSmallestAngle();

        if (angleBetweenVectors.isNullAngle()) {
            return toSegmentsOneDirection(
                    current,
                    next
            );
        }

        if (Vector.magnitude(vector) == 0) {
            // ROTATE ONLY
            return Stream.of(Segment.builder()
                    .distance(0) // ???
                    .angle(angleBetweenVectors.toDegrees())
                    .forward(true)
                    .point(current.getPoint())
                    .direction(current.getDirection())
                    .build()
            );
        }

        // COMPLEX
        final var line1 = current.getPoint().withDirection(current.getDirection());
        final var line2 = next.getPoint().withDirection(next.getDirection());

        return Line2d.intersect(
                line1,
                line2
        )
                .map(intersection -> toSegmentsWithIntersection(
                        current,
                        intersection,
                        next
                ))
                .orElseGet(() -> toSegmentsWithoutIntersection(
                        current,
                        next
                ));
    }

    private static Stream<Segment> toSegmentsWithoutIntersection(
            KeyFrame current,
            KeyFrame next
    ) {
        // INSERT POINT BETWEEN BOTH AND RETRY
        // TODO
        return Stream.empty();
    }

    private static Stream<Segment> toSegmentsWithIntersection(
            final KeyFrame current,
            final Point2d intersection,
            final KeyFrame next
    ) {
        final var vector1 = current.getPoint().min(intersection);
        final var vector2 = intersection.min(next.getPoint());

        final var angle = Vector.angle2d(vector1, vector2);

        final var forward = angle.getRadian() < 0;
        final var lrFactor = forward ? 1 : -1;

        final var magnitude1 = Vector.magnitude(vector1);
        final var magnitude2 = Vector.magnitude(vector2);

        if (magnitude1 <= magnitude2) {
            final var point2b = intersection.add(vector2.normalize().dot(magnitude1));

            final var p1_to_p2b = current.getPoint().min(point2b);
            final var p1_to_p2b_length = p1_to_p2b.magnitude();

            //final var center = previousAction.getPoint().add(p1_to_p2b.rotateZ(Math.PI / 3));

            return Stream.concat(
                    Stream.of(
                            Segment.builder()
                                    .distance(Math.PI * p1_to_p2b_length / 6)
                                    .radius(p1_to_p2b_length)
                                    .angle(SIXTY_DEGREE)
                                    .forward(forward)
                                    .point(current.getPoint())
                                    .direction(current.getDirection())
                                    .build()
                    ),
                    // MOVE STRAIGHT FROM point2b to point2
                    toSegmentsOneDirection(
                            KeyFrame.builder()
                                    .point(point2b)
                                    .direction(next.getDirection())
                                    .build(),
                            next
                    )
            );
        }

        final var point1b = current.getPoint().add(vector1.normalize().dot(magnitude2 - magnitude1));

        final var p1b_to_p2 = next.getPoint().min(point1b);
        final var p1b_to_p2_length = p1b_to_p2.magnitude();

        //final var center = previousAction.getPoint().add(p1_to_p2b.rotateZ(Math.PI / 3));

        return Stream.concat(
                // MOVE STRAIGHT FROM point1 to point1b
                toSegmentsOneDirection(
                        current,
                        KeyFrame.builder()
                                .point(point1b)
                                .direction(current.getDirection())
                                .build()
                ),
                Stream.of(
                        Segment.builder()
                                .distance(Math.PI * p1b_to_p2_length / 6)
                                .radius(p1b_to_p2_length)
                                .angle(SIXTY_DEGREE)
                                .forward(forward)
                                .point(current.getPoint())
                                .direction(current.getDirection())
                                .build()
                )
        );
    }

    private static Stream<Segment> toSegmentsOneDirection(
            KeyFrame current,
            KeyFrame next
    ) {
        final var vector = current.getPoint().min(next.getPoint());
        final var distanceBetweenPoints = Vector.magnitude(vector);

        final var previousAngle = Vector.angle(
                vector,
                current.getDirection()
        );

        if (previousAngle.isNullAngle()) {
            // MOVE FORWARD
            return Stream.of(Segment.builder()
                    .distance(distanceBetweenPoints)
                    .forward(true)
                    .point(current.getPoint())
                    .direction(current.getDirection())
                    .build()
            );
        }

        if (previousAngle.isStraight()) {
            // MOVE BACKWARD
            return Stream.of(Segment.builder()
                    .distance(distanceBetweenPoints)
                    .forward(false)
                    .point(current.getPoint())
                    .direction(current.getDirection())
                    .build()
            );
        }

        // INSERT POINT BETWEEN BOTH AND RETRY
        return Stream.empty();
        //throw new UnsupportedOperationException("NOT YET IMPLEMENTED");
    }

    private static List<Segment> toSegments(
            final List<KeyFrame> keyFrames
    ) {
        return IntStream.range(
                0,
                keyFrames.size()
        )
                .boxed()
                .flatMap(i -> toSegments(
                        keyFrames.get(i),
                        keyFrames.get(i + 1)
                ))
                .collect(Collectors.toList());
    }

    public void execute(
            final Scene scene
    ) {
        scene.getRobots()
                .forEach((robot, configuration) -> {
                    robotService.registerByName(robot);
                    robotService.configureByName(robot, configuration);
                });

        scene.getCameras()
                .forEach(cameraService::registerByName);

        scene.getCameras()
                .keySet()
                .forEach(camera -> {
                    cameraService.changeSetting(camera, Camera.General.AutoOff.NEVER);
                    cameraService.changeSubMode(camera, Camera.SubMode.PHOTO_SINGLE);
                });

        final var segmentMap = scene.getKeyFrames()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> toSegments(entry.getValue())
                ));

        segmentMap.forEach((robotName, segments) -> segments.forEach(segment -> {
            if (segment.getRadius() > 0) {
                robotService.handle(
                        robotName,
                        Timelapse.TravelArc.builder()
                                .radius(segment.isForward()
                                        ? segment.getRadius()
                                        : -segment.getRadius()
                                )
                                .angle(segment.isForward()
                                        ? segment.getAngle()
                                        : -segment.getAngle()
                                )
                                .build()
                );
            } else if (segment.getAngle() > 0) {
                robotService.handle(
                        robotName,
                        Timelapse.Rotate.builder()
                                .angle(segment.isForward()
                                        ? segment.getAngle()
                                        : -segment.getAngle()
                                )
                                .build()
                );
            } else {
                robotService.handle(
                        robotName,
                        Timelapse.MoveForward.builder()
                                .distance(segment.isForward()
                                        ? segment.getDistance()
                                        : -segment.getDistance()
                                )
                                .build()
                );
            }
        }));

        /*
        for (final KeyFrame action : scene.getKeyFrames()) {
            if (Objects.isNull(previousAction)) {
                previousAction = action;
                continue;
            }

            final var vector = previousAction.getPoint().min(action.getPoint());
            final var angleBetweenPoints = vector.angle2d().toSmallestAngle();
            final var angleBetweenVectors = Vector.angle2d(
                    previousAction.getDirection(),
                    action.getDirection()
            )
                    .toSmallestAngle();
            final var distanceBetweenPoints = Vector.magnitude(vector);

            if (angleBetweenVectors.isNullAngle()) {

                final var previousAngle = Vector.angle(vector, previousAction.getDirection());

                if (previousAngle.isNullAngle()) {
                    // MOVE FORWARD
                    robotService.handle(
                            action.getRobotName(),
                            Timelapse.MoveForward.builder()
                                    .distance(distanceBetweenPoints)
                                    .build()
                    );
                } else if (previousAngle.isStraight()) {
                    // MOVE BACKWARD
                    robotService.handle(
                            action.getRobotName(),
                            Timelapse.MoveForward.builder()
                                    .distance(-distanceBetweenPoints)
                                    .build()
                    );
                } else {
                    // INSERT POINT BETWEEN BOTH AND RETRY
                    throw new UnsupportedOperationException("NOT YET IMPLEMENTED");
                }

            } else if (distanceBetweenPoints == 0) {
                // ROTATE ONLY
                robotService.handle(
                        action.getRobotName(),
                        Timelapse.Rotate.builder()
                                .angle(angleBetweenVectors.toDegrees())
                                .build()
                );
            } else {
                // COMPLEX

                final var line1 = previousAction.getPoint().withDirection(previousAction.getDirection());
                final var line2 = action.getPoint().withDirection(action.getDirection());
                // COMPLEX MOVEMENT
                final var intersection = Line2d.intersect(
                        line1,
                        line2
                );

                if (intersection.isPresent()) {
                    final var intersectionPoint = intersection.get();
                    final var vector1 = previousAction.getPoint().min(intersectionPoint);
                    final var vector2 = intersectionPoint.min(action.getPoint());

                    final var angle = Vector.angle2d(vector1, vector2);

                    final var lrFactor = angle.getRadian() < 0 ? 1 : -1;

                    final var magnitude1 = Vector.magnitude(vector1);
                    final var magnitude2 = Vector.magnitude(vector2);

                    if (magnitude1 <= magnitude2) {
                        final var point2b = intersectionPoint.add(vector2.normalize().dot(magnitude1));

                        final var p1_to_p2b = previousAction.getPoint().min(point2b);
                        final var p1_to_p2b_length = p1_to_p2b.magnitude();

                        //final var center = previousAction.getPoint().add(p1_to_p2b.rotateZ(Math.PI / 3));
                        robotService.handle(
                                action.getRobotName(),
                                Timelapse.TravelArc.builder()
                                        .radius(lrFactor * p1_to_p2b_length)
                                        .angle(lrFactor * SIXTY_DEGREE)
                                        .build()
                        );

                        // MOVE STRAIGHT FROM point2b to point2
                        robotService.handle(
                                action.getRobotName(),
                                Timelapse.MoveForward.builder()
                                        .distance(magnitude1)
                                        .build()
                        );
                    } else {
                        final var point1b = previousAction.getPoint().add(vector1.normalize().dot(magnitude2 - magnitude1));
                        // MOVE STRAIGHT FROM point1 to point1b
                        robotService.handle(
                                action.getRobotName(),
                                Timelapse.MoveForward.builder()
                                        .distance(magnitude2 - magnitude1)
                                        .build()
                        );

                        final var p1b_to_p2 = action.getPoint().min(point1b);
                        final var p1b_to_p2_length = p1b_to_p2.magnitude();

                        //final var center = previousAction.getPoint().add(p1_to_p2b.rotateZ(Math.PI / 3));
                        robotService.handle(
                                action.getRobotName(),
                                Timelapse.TravelArc.builder()
                                        .radius(lrFactor * p1b_to_p2_length)
                                        .angle(lrFactor * SIXTY_DEGREE)
                                        .build()
                        );
                    }

                } else {
                    // INSERT POINT BETWEEN BOTH AND RETRY
                    // TODO
                    throw new UnsupportedOperationException("NOT YET IMPLEMENTED");
                }
            }

            previousAction = action;
        }
        */
    }

}
