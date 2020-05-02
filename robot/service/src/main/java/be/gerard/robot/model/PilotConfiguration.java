package be.gerard.robot.model;

import lejos.robotics.navigation.MoveController;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PilotConfiguration {
    @Builder.Default
    double wheelDiameter = MoveController.WHEEL_SIZE_EV3;
    double trackWidth;
    String leftMotor;
    String rightMotor;
}
