package be.gerard.robot.service;

import be.gerard.robot.model.Controls;
import be.gerard.robot.model.Ev3Robot;
import be.gerard.robot.model.PilotConfiguration;
import lejos.hardware.BrickFinder;
import lejos.hardware.BrickInfo;
import lejos.remote.ev3.RemoteRequestEV3;
import lejos.robotics.navigation.ArcRotateMoveController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class RobotService {

    private static final Map<String, Ev3Robot> ROBOT_MAP = new ConcurrentHashMap<>();

    private static Optional<Ev3Robot> findByName(
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
        final String ip = findIpByName(name)
                .orElseThrow(() -> new IllegalArgumentException(String.format(
                        "robot not found [name=%s]",
                        name
                )));

        final Ev3Robot robot = connectByIp(ip)
                .orElseThrow(() -> new IllegalStateException(String.format(
                        "robot not connected [name=%s,ip=%s]",
                        name,
                        ip
                )));

        ROBOT_MAP.put(name, robot);

        return ip;
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
            final String pilotId,
            final PilotConfiguration configuration
    ) {
        final Ev3Robot robot = getByName(name);

        if (robot.getPilotMap().containsKey(pilotId)) {
            throw new IllegalArgumentException(String.format(
                    "robot already contains the given pilotId [name=%s,pilotId=%s]",
                    name,
                    pilotId
            ));
        }

        final ArcRotateMoveController pilot = robot.getEv3()
                .createPilot(
                        configuration.getWheelDiameter(),
                        configuration.getTrackWidth(),
                        configuration.getLeftMotor(),
                        configuration.getRightMotor()
                );

        robot.getPilotMap().put(pilotId, pilot);
    }

    public void handle(
            final String name,
            final String pilotId,
            final Controls.Control.Movement command
    ) {
        findByName(name)
                .flatMap(robot -> robot.findById(pilotId))
                .ifPresent(command::execute);
    }

    @EventListener
    public void handle(
            final Controls.Register event
    ) {
        if (findByName(event.getName()).isEmpty()) {
            registerByName(event.getName());
        }

        configureByName(
                event.getName(),
                event.getPilotId(),
                event.getPilotConfiguration()
        );
    }

    @EventListener
    public void handle(
            final Controls.MoveForward event
    ) {
        //handle(
        //        event.getName(),
        //        event.getPilotId(),
        //        event
        //);
    }

}
