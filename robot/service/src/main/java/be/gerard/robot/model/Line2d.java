package be.gerard.robot.model;

import lombok.Value;

import java.util.Optional;

@Value(staticConstructor = "of")
public class Line2d {
    Point2d point;
    Vector direction;

    public static Optional<Point2d> intersect(
            final Line2d l1,
            final Line2d l2
    ) {
        // inspired by https://www.geeksforgeeks.org/program-for-point-of-intersection-of-two-lines/

        double a1 = l1.getDirection().getY();
        double b1 = -l1.getDirection().getX();
        double c1 = a1 * l1.getPoint().getX() + b1 * l1.getPoint().getY(); // a1x + b1y = c1

        double a2 = l2.getDirection().getY();
        double b2 = -l2.getDirection().getX();
        double c2 = a2 * l2.getPoint().getX() + b2 * l2.getPoint().getY(); // a2x + b2y = c2

        double determinant = a1 * b2 - a2 * b1;

        if (determinant == 0) {
            return Optional.empty();
        }

        double x = (b2 * c1 - b1 * c2) / determinant;
        double y = (a1 * c2 - a2 * c1) / determinant;

        return Optional.of(Point2d.of(
                x,
                y
        ));
    }

}
