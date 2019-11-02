package be.gerard.robot.web;

import be.gerard.robot.model.Controls;
import be.gerard.robot.model.PilotConfiguration;
import be.gerard.robot.service.RobotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("robots")
public class RobotRestController {

    private final RobotService robotService;

    @PutMapping("registrations/{name}")
    public String registerByName(
            @PathVariable("name") final String name
    ) {
        return robotService.registerByName(name);
    }

    @DeleteMapping("registrations/{name}")
    public void unregisterByName(
            @PathVariable("name") final String name
    ) {
        robotService.unregisterByName(name);
    }

    @PutMapping("{name}/pilots/{pilotId}")
    public void configureByName(
            @PathVariable("name") final String name,
            @PathVariable("pilotId") final String pilotId,
            @RequestBody final PilotConfiguration configuration
    ) {
        robotService.configureByName(name, pilotId, configuration);
    }

    @PutMapping("{name}/pilots/{pilotId}/arc")
    public void move(
            @PathVariable("name") final String name,
            @PathVariable("pilotId") final String pilotId,
            @RequestBody final Controls.TravelArc command
    ) {
        robotService.handle(name, pilotId, command);
    }

    @PutMapping("{name}/pilots/{pilotId}/forward")
    public void move(
            @PathVariable("name") final String name,
            @PathVariable("pilotId") final String pilotId,
            @RequestBody final Controls.MoveForward command
    ) {
        robotService.handle(name, pilotId, command);
    }

    @PutMapping("{name}/pilots/{pilotId}/rotate")
    public void move(
            @PathVariable("name") final String name,
            @PathVariable("pilotId") final String pilotId,
            @RequestBody final Controls.Rotate command
    ) {
        robotService.handle(name, pilotId, command);
    }

}
