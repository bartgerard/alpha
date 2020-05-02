package be.gerard.robot.service;

import be.gerard.robot.model.Timelapse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TimelapseService {

    private final RobotService robotService;

    @EventListener
    public void handle(
            final Timelapse.Register event
    ) {
        if (RobotService.findByName(event.getName()).isEmpty()) {
            robotService.registerByName(event.getName());
        }

        robotService.configureByName(
                event.getName(),
                event.getPilotId(),
                event.getPilotConfiguration()
        );
    }

    @EventListener
    public void handle(
            final Timelapse.TravelArc event
    ) {
        robotService.handle(
                event.getName(),
                event.getPilotId(),
                event
        );
    }

    @EventListener
    public void handle(
            final Timelapse.MoveForward event
    ) {
        robotService.handle(
                event.getName(),
                event.getPilotId(),
                event
        );
    }

    @EventListener
    public void handle(
            final Timelapse.Rotate event
    ) {
        robotService.handle(
                event.getName(),
                event.getPilotId(),
                event
        );
    }

    @EventListener
    public void handle(
            final Timelapse.Deregister event
    ) {
        robotService.unregisterByName(
                event.getName()
        );
    }

}
