package be.gerard.robot.model;

import lejos.robotics.navigation.MoveController;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Builder
public class PilotConfiguration {
    @Builder.Default
    private final double wheelDiameter = MoveController.WHEEL_SIZE_EV3;
    private final double trackWidth;
    private final String leftMotor;
    private final String rightMotor;
}
