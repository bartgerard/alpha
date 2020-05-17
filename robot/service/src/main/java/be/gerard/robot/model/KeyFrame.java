package be.gerard.robot.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class KeyFrame {
    String robotName;
    Point2d point;
    Vector direction;
}
