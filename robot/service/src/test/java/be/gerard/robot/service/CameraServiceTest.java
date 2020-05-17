package be.gerard.robot.service;

import be.gerard.robot.RobotApplication;
import be.gerard.robot.model.Camera;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {
        RobotApplication.class
})
public class CameraServiceTest {

    @Autowired
    private CameraService cameraService;

    @Disabled
    @Test
    public void test() throws IOException {
        cameraService.registerByName("CAM0", "10.5.5.9");


        //final var mediaList = cameraService.getMediaList("CAM0");
        //final var thumbnail = cameraService.getThumbnail(
        //        "CAM0",
        //        "100GOPRO",
        //        "GOPR0344.JPG"
        //);
//
        //Files.write(
        //        Paths.get("./test.jpg"),
        //        thumbnail
        //);

        cameraService.changeDefaultBootMode("CAM0", Camera.Mode.PHOTO);
        cameraService.changeMode("CAM0", Camera.Mode.PHOTO);
        //cameraService.changeSubMode("CAM0", Camera.SubMode.PHOTO_NIGHT);
        cameraService.shutter("CAM0", Camera.Shutter.Action.TRIGGER);

        cameraService.changeSetting("CAM0", Camera.CaptureMode.Photo.ProTune.OFF);
        cameraService.changeSetting("CAM0", Camera.CaptureMode.Photo.ProTune.ON);

        cameraService.changeSetting("CAM0", Camera.General.AutoOff.NEVER);

        cameraService.sleep("CAM0");
    }

}
