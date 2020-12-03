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
        return angle.toSmallestAngle();
    }

    /**
     * Stompe hoek
     */
    public static boolean isObtuse(
            final Angle angle
    ) {
        return angle.isObtuse();
    }

    /**
     * Scherpe hoek
     */
    public static boolean isAcute(
            final Angle angle
    ) {
        return angle.isAcute();
    }

    public static boolean isRight(
            final Angle angle
    ) {
        return angle.isRight();
    }

    public static boolean isNullAngle(
            final Angle angle
    ) {
        return angle.isNullAngle();
    }

    public static boolean isStraight(
            final Angle angle
    ) {
        return angle.isStraight();
    }

    public Angle toSmallestAngle() {
        if (Math.abs(this.radian) <= Math.PI) {
            return this;
        }

        final var modulo360 = this.radian % (Math.PI * 2);

        return Angle.of(
                modulo360 > Math.PI
                        ? modulo360 - Math.PI * 2
                        : modulo360
        );
    }

    public boolean isObtuse() {
        return Math.abs(this.radian) > Math.PI * 0.5;
    }

    public boolean isAcute() {
        return Math.abs(this.radian) < Math.PI * 0.5;
    }

    public boolean isRight() {
        return Math.abs(this.radian) == Math.PI * 0.5;
    }

    public boolean isNullAngle() {
        return this.radian == 0;
    }

    public boolean isStraight() {
        return this.radian == Math.PI;
    }

    public double toRadians() {
        return radian;
    }

    public double toDegrees() {
        return Math.toDegrees(radian);
    }

    public Angle min(
            final Angle other
    ) {
        return Angle.of(this.radian - other.radian);
    }

}
