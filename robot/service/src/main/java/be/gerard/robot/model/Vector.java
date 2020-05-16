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

    public static double angle(
            final Vector v1,
            final Vector v2
    ) {
        return Math.acos(dot(v1, v2) / (magnitude(v1) * magnitude(v2)));
    }

    public static double angle2d(
            final Vector v1,
            final Vector v2
    ) {
        final var angle1 = v1.angle2d();
        final var angle2 = v2.angle2d();

        return angle1 - angle2;
    }

    public static boolean isSameAngle(
            final double angle1,
            final double angle2
    ) {
        return Math.toDegrees(angle1) == Math.toDegrees(angle2);
    }

    public Vector normalize() {
        return normalize(this);
    }

    public double angle2d() {
        return Math.atan2(
                this.y,
                this.x
        );
    }

}