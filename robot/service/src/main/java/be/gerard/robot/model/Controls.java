package be.gerard.robot.model;

import lejos.robotics.navigation.ArcRotateMoveController;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Controls {

    public interface Control {

        interface Movement extends Control {

            void execute(
                    ArcRotateMoveController pilot
            );

        }

    }

    @Value
    public static class Register implements Control {
        String name;
        String pilotId;
        PilotConfiguration pilotConfiguration;
    }

    @Value
    public static class Deregister implements Control {
        String name;
    }

    @Value
    public static class TravelArc implements Control.Movement {
        double radius;
        double angle;

        public void execute(
                final ArcRotateMoveController pilot
        ) {
            pilot.arc(radius, angle);
        }

    }

    @Value
    public static class MoveForward implements Control.Movement {
        double distance;

        public void execute(
                final ArcRotateMoveController pilot
        ) {
            pilot.travel(distance);
        }

    }

    @Value
    public static class Rotate implements Control.Movement {
        double angle;

        public void execute(
                final ArcRotateMoveController pilot
        ) {
            pilot.rotate(angle);
        }

    }

}
