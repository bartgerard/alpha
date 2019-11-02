package be.gerard.robot.web;

import be.gerard.robot.service.RobotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
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

}
