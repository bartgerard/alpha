package be.gerard.robot.model;

import lombok.Value;

@Value(staticConstructor = "of")
public class Vector {
    double x;
    double y;
    double z;

    public static Vector of(
            final double x,
            final double y
    ) {
        return Vector.of(x, y, 0);
    }

    public static double magnitude(
            final Vector vector
    ) {
        return Math.abs(Math.sqrt(
                vector.x * vector.x + vector.y * vector.y + vector.z * vector.z
        ));
    }

    public static Vector normalize(
            final Vector vector
    ) {
        final var magnitude = magnitude(vector);
        return Vector.of(
                vector.x / magnitude,
                vector.y / magnitude,
                vector.z / magnitude
        );
    }

    public static double dot(
            final Vector v1,
            final Vector v2
    ) {
        return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
    }

    public static Angle angle(
            final Vector v1,
            final Vector v2
    ) {
        return Angle.of(Math.acos(
                dot(v1, v2) / (magnitude(v1) * magnitude(v2))
        ));
    }

    public static Angle angle2d(
            final Vector v1,
            final Vector v2
    ) {
        final var angle1 = v1.angle2d();
        final var angle2 = v2.angle2d();

        return angle1.min(angle2);
    }

    public static Angle angle2d(
            final Vector vector
    ) {
        return Angle.of(Math.atan2(
                vector.y,
                vector.x
        ));
    }

    public static Vector rotateZ(
            final Vector vector,
            final double angle
    ) {
        return Vector.of(
                vector.x * Math.cos(angle) - vector.y * Math.sin(angle),
                vector.x * Math.sin(angle) + vector.y * Math.cos(angle)
        );
    }

    public static Vector rotate(
            final Vector vector,
            final Vector axis,
            final double angle
    ) {
        final double x = vector.getX();
        final double y = vector.getY();
        final double z = vector.getZ();

        final double u = axis.getX();
        final double v = axis.getY();
        final double w = axis.getZ();

        final var dotProduct = dot(vector, axis);

        double xPrime = u * dotProduct * (1d - Math.cos(angle))
                + x * Math.cos(angle)
                + (-w * y + v * z) * Math.sin(angle);
        double yPrime = v * dotProduct * (1d - Math.cos(angle))
                + y * Math.cos(angle)
                + (w * x - u * z) * Math.sin(angle);
        double zPrime = w * dotProduct * (1d - Math.cos(angle))
                + z * Math.cos(angle)
                + (-v * x + u * y) * Math.sin(angle);

        return Vector.of(
                xPrime,
                yPrime,
                zPrime
        );
    }

    public double magnitude() {
        return magnitude(this);
    }

    public Vector normalize() {
        return normalize(this);
    }

    public Angle angle2d() {
        return angle2d(this);
    }

    public Line2d atPoint(
            final Point2d point
    ) {
        return Line2d.of(
                point,
                this
        );
    }

    public double getSlope() {
        return this.y / this.x;
    }

    public Vector dot(
            final double c
    ) {
        return Vector.of(
                this.x * c,
                this.y * c,
                this.z * c
        );
    }

    public Vector rotateZ(
            final double angle
    ) {
        return rotateZ(
                this,
                angle
        );
    }

}
