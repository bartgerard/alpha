package be.gerard.robot.model;

import lejos.remote.ev3.RemoteRequestEV3;
import lejos.robotics.navigation.ArcRotateMoveController;
import lejos.robotics.navigation.MoveController;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
@Builder
public class Ev3Robot {

    private final String ip;
    private final RemoteRequestEV3 ev3;

    private final Map<String, ArcRotateMoveController> pilotMap = new HashMap<>();

    public Optional<ArcRotateMoveController> findById(
            final String pilotId
    ) {
        return Optional.ofNullable(pilotMap.get(pilotId));
    }

    public void disconnect() {
        pilotMap.values().forEach(MoveController::stop);
        this.ev3.disConnect();
    }

}
