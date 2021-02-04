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
    double angularSpeed = 202.1796417236328; // degrees per second
    double linearSpeed = 24.700902938842773; // cm per second
    double linearAcceleration = 98; // chosen unit / s^2
}
