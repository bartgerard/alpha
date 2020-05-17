package be.gerard.robot.model;

import lombok.Value;

import java.util.Objects;

@Value(staticConstructor = "of")
public class Angle {

    double radian;

    public static boolean isSameAngle(
            final Angle angle1,
            final Angle angle2
    ) {
        return Objects.equals(
                toSmallestAngle(angle1),
                toSmallestAngle(angle2)
        );
    }

    /**
     * Limit to Math.PI and -Math.PI
     */
    public static Angle toSmallestAngle(
            final Angle angle
    ) {
        if (Math.abs(angle.radian) <= Math.PI) {
            return angle;
        }

        final var modulo360 = angle.radian % (Math.PI * 2);

        return Angle.of(
                modulo360 > Math.PI
                        ? modulo360 - Math.PI * 2
                        : modulo360
        );
    }

    /**
     * Stompe hoek
     */
    public static boolean isObtuse(
            final Angle angle
    ) {
        return Math.abs(angle.radian) > Math.PI * 0.5;
    }

    /**
     * Scherpe hoek
     */
    public static boolean isAcute(
            final Angle angle
    ) {
        return Math.abs(angle.radian) < Math.PI * 0.5;
    }

    public static boolean isRight(
            final Angle angle
    ) {
        return Math.abs(angle.radian) == Math.PI * 0.5;
    }

    public static boolean isNullAngle(
            final Angle angle
    ) {
        return angle.radian == 0;
    }

    public Angle toSmallestAngle() {
        return toSmallestAngle(this);
    }

    public boolean isObtuse() {
        return isObtuse(this);
    }

    public boolean isAcute() {
        return isAcute(this);
    }

    public boolean isRight() {
        return isRight(this);
    }

    public boolean isNullAngle() {
        return isNullAngle(this);
    }

    public double getDegrees() {
        return Math.toDegrees(radian);
    }

    public Angle min(
            final Angle other
    ) {
        return Angle.of(this.radian - other.radian);
    }

}
