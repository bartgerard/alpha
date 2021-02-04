package be.gerard.robot.model;

import lejos.robotics.navigation.ArcRotateMoveController;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Movement {

    public interface Control {

        void execute(
                ArcRotateMoveController pilot
        );

    }

    public interface TravelArc extends Movement.Control {

        double getRadius();

        double getAngle();

        default void execute(
                final ArcRotateMoveController pilot
        ) {
            pilot.arc(getRadius(), getAngle());
        }

    }

    public interface MoveForward extends Movement.Control {

        double getDistance();

        default void execute(
                final ArcRotateMoveController pilot
        ) {
            pilot.travel(getDistance());
        }

    }

    public interface Rotate extends Movement.Control {

        double getAngle();

        default void execute(
                final ArcRotateMoveController pilot
        ) {
            pilot.rotate(getAngle());
        }

    }

}
