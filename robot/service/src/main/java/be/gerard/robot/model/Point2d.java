package be.gerard.robot.model;

import lombok.Value;

@Value(staticConstructor = "of")
public class Point2d {
    double x;
    double y;

    public static Vector min(
            final Point2d p1,
            final Point2d p2
    ) {
        return Vector.of(
                p2.x - p1.x,
                p2.y - p1.y
        );
    }

    public Vector min(
            final Point2d other
    ) {
        return min(
                this,
                other
        );
    }

}
