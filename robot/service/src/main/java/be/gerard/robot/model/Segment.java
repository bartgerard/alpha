package be.gerard.robot.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Segment {

    double distance;
    double radius;
    double angle;

    boolean forward;

    Point2d point;
    Vector direction;

}
