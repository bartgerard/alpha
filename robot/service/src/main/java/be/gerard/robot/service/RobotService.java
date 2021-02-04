package be.gerard.robot.service;

import be.gerard.robot.model.Ev3Robot;
import be.gerard.robot.model.Movement;
import be.gerard.robot.model.PilotConfiguration;
import lejos.hardware.BrickFinder;
import lejos.hardware.BrickInfo;
import lejos.remote.ev3.RemoteRequestEV3;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class RobotService {

    private static final String DEFAULT_PILOT_ID = "main";

    private static final Map<String, Ev3Robot> ROBOT_MAP = new ConcurrentHashMap<>();

    static Optional<Ev3Robot> findByName(
            final String name
    ) {
        return Optional.ofNullable(ROBOT_MAP.get(name));
    }

    private static Ev3Robot getByName(
            final String name
    ) {
        return findByName(name).orElseThrow(() -> new IllegalArgumentException(String.format(
                "robot not registered [name=%s]",
                name
        )));
    }

    public String registerByName(
            final String name
    ) {
        return ROBOT_MAP.computeIfAbsent(
                name,
                robotName -> {
                    final String ip = findIpByName(robotName)
                            .orElseThrow(() -> new IllegalArgumentException(String.format(
                                    "robot not found [name=%s]",
                                    robotName
                            )));

                    return connectByIp(ip)
                            .orElseThrow(() -> new IllegalStateException(String.format(
                                    "robot not connected [name=%s,ip=%s]",
                                    robotName,
                                    ip
                            )));
                }
        )
                .getIp();
    }

    private Optional<String> findIpByName(
            final String name
    ) {
        final BrickInfo[] bricks = BrickFinder.discover();

        for (final BrickInfo info : bricks) {

            if (Objects.equals(info.getName(), name)) {
                return Optional.of(info.getIPAddress());
            }
        }

        return Optional.empty();
    }

    private Optional<Ev3Robot> connectByIp(
            final String ip
    ) {
        try {
            return Optional.of(Ev3Robot.builder()
                    .ip(ip)
                    .ev3(new RemoteRequestEV3(ip))
                    .build()

            );
        } catch (IOException e) {
            log.error("discovery exception", e);
        }

        return Optional.empty();
    }

    public void unregisterByName(
            final String name
    ) {
        final Ev3Robot robot = ROBOT_MAP.get(name);
        ROBOT_MAP.remove(name);

        robot.disconnect();
    }

    public void configureByName(
            final String name,
            final PilotConfiguration configuration
    ) {
        configureByName(
                name,
                DEFAULT_PILOT_ID,
                configuration
        );
    }

    public void configureByName(
            final String name,
            final String pilotId,
            final PilotConfiguration configuration
    ) {
        final Ev3Robot robot = getByName(name);

        robot.getPilotMap()
                .computeIfAbsent(
                        pilotId,
                        pid -> {
                            final var pilot = robot.getEv3()
                                    .createPilot(
                                            configuration.getWheelDiameter(),
                                            configuration.getTrackWidth(),
                                            configuration.getLeftMotor(),
                                            configuration.getRightMotor()
                                    );

                            pilot.setAngularSpeed(configuration.getAngularSpeed());
                            pilot.setLinearSpeed(configuration.getLinearSpeed());
                            pilot.setLinearAcceleration(configuration.getLinearAcceleration());

                            return pilot;
                        }
                );
    }

    public void handle(
            final String name,
            final Movement.Control command
    ) {
        handle(
                name,
                DEFAULT_PILOT_ID,
                command
        );
    }

    public void handle(
            final String name,
            final String pilotId,
            final Movement.Control command
    ) {
        findByName(name)
                .flatMap(robot -> robot.findById(pilotId))
                .ifPresent(command::execute);
    }

}
