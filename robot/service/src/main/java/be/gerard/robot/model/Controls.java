package be.gerard.robot.model;

import lejos.robotics.navigation.ArcRotateMoveController;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Controls {

    public interface Control {

        void execute(
                ArcRotateMoveController pilot
        );

    }

    @RequiredArgsConstructor
    @Getter
    public static class TravelArc implements Control {
        private final double radius;
        private final double angle;

        public void execute(
                final ArcRotateMoveController pilot
        ) {
            pilot.arc(radius, angle);
        }

    }

    @RequiredArgsConstructor
    @Getter
    public static class MoveForward implements Control {
        private final double distance;

        public void execute(
                final ArcRotateMoveController pilot
        ) {
            pilot.travel(distance);
        }

    }

    @RequiredArgsConstructor
    @Getter
    public static class Rotate implements Control {
        private final double angle;

        public void execute(
                final ArcRotateMoveController pilot
        ) {
            pilot.rotate(angle);
        }

    }

}
