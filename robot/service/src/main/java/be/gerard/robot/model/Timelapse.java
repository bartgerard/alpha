package be.gerard.robot.model;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@NoArgsConstructor
public final class Timelapse {

    public interface Control {
    }

    @Value
    @Builder
    public static class RegisterCamera implements Timelapse.Control {
        String name;
        String ip;
    }

    @Value
    @Builder
    public static class RegisterEv3 implements Timelapse.Control {
        String name;
        String pilotId;
        PilotConfiguration pilotConfiguration;
    }

    @Value
    @Builder
    public static class Deregister implements Timelapse.Control {
        String name;
    }

    @Value
    @Builder
    public static class TravelArc implements Timelapse.Control, Movement.TravelArc {
        String name;
        String pilotId;
        double radius;
        double angle;
    }

    @Value
    @Builder
    public static class MoveForward implements Timelapse.Control, Movement.MoveForward {
        String name;
        String pilotId;
        double distance;
    }

    @Value
    @Builder
    public static class Rotate implements Timelapse.Control, Movement.Rotate {
        String name;
        String pilotId;
        double angle;
    }

}
