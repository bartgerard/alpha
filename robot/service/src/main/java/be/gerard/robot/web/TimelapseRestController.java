package be.gerard.robot.web;

import be.gerard.robot.model.Scene;
import be.gerard.robot.service.DirectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("timelapse")
public class TimelapseRestController {
    private final DirectorService directorService;

    @PutMapping("execute")
    public void execute(
            @RequestBody final Scene scene
    ) {
        directorService.execute(scene);
    }

}
