package be.gerard.robot.service;

import be.gerard.robot.RobotApplication;
import be.gerard.robot.model.Camera;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {
        RobotApplication.class
})
public class CameraServiceTest {

    @Autowired
    private CameraService cameraService;

    @Test
    public void test() {
        cameraService.registerByName("CAM0", "10.5.5.9");
        cameraService.changeDefaultBootMode("CAM0", Camera.Mode.PHOTO);
        cameraService.changeMode("CAM0", Camera.Mode.PHOTO);
        //cameraService.changeSubMode("CAM0", Camera.SubMode.PHOTO_NIGHT);
        cameraService.shutter("CAM0", Camera.Shutter.Action.TRIGGER);
        cameraService.sleep("CAM0");
    }

}
