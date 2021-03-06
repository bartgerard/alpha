package be.gerard.robot.model;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.List;
import java.util.Map;

@Value
@Builder
public class Scene {
    @Singular
    Map<String, PilotConfiguration> robots;
    @Singular
    Map<String, String> cameras;
    @Singular
    Map<String, List<KeyFrame>> keyFrames;
}
