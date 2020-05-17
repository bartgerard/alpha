package be.gerard.robot.service;

import be.gerard.robot.model.Camera;
import be.gerard.robot.model.KeyFrame;
import be.gerard.robot.model.Line2d;
import be.gerard.robot.model.Scene;
import be.gerard.robot.model.Timelapse;
import be.gerard.robot.model.Vector;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DirectorService {

    public static final int SIXTY_DEGREE = 60;
    private final RobotService robotService;
    private final CameraService cameraService;

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

        KeyFrame previousAction = null;

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

    }

}
