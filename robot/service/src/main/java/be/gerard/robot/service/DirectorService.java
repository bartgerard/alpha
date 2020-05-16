package be.gerard.robot.service;

import be.gerard.robot.model.Camera;
import be.gerard.robot.model.Scene;
import be.gerard.robot.model.Timelapse;
import be.gerard.robot.model.Vector;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DirectorService {

    private final RobotService robotService;
    private final CameraService cameraService;

    public void execute(
            final Scene scene
    ) {
        System.out.println(scene);

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


        final var robotName = "EV3";
        final var actions = scene.getRobotActions().get(robotName);

        Scene.RobotAction previousAction = null;

        for (final Scene.RobotAction action : actions) {
            if (Objects.isNull(previousAction)) {
                previousAction = action;
                continue;
            }

            final var angleBetweenVectors = Vector.angle2d(
                    previousAction.getVector(),
                    action.getVector()
            );
            final var vector = previousAction.getPoint().min(action.getPoint());
            final var angleBetweenPoints = vector.angle2d();
            final var isSameAngle = Vector.isSameAngle(
                    angleBetweenVectors,
                    angleBetweenPoints
            );

            if (angleBetweenVectors == 0) {

                if (isSameAngle) {
                    // MOVE FORWARD
                    robotService.handle(
                            robotName,
                            Timelapse.MoveForward.builder()
                                    .name(robotName)
                                    .distance(Vector.magnitude(vector))
                                    .build()
                    );
                } else {
                    // COMPLEX MOVEMENT
                }

            } else if (Objects.equals(previousAction.getPoint(), action.getPoint())) {
                // ROTATE ONLY
            } else {
                // COMPLEX
            }
        }

    }

}
