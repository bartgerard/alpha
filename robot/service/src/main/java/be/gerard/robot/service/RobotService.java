package be.gerard.robot.service;

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

    private static final Map<String, RemoteRequestEV3> DEVICE_MAP = new ConcurrentHashMap<>();

    public String registerByName(
            final String name
    ) {
        final String ip = findIpByName(name)
                .orElseThrow(() -> new IllegalArgumentException(String.format(
                        "device not found [name=%s]",
                        name
                )));

        final RemoteRequestEV3 ev3 = connectByIp(ip)
                .orElseThrow(() -> new IllegalStateException(String.format(
                        "device not connected [name=%s,ip=%s]",
                        name,
                        ip
                )));


        DEVICE_MAP.put(name, ev3);

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

    private Optional<RemoteRequestEV3> connectByIp(
            final String ip
    ) {
        try {
            return Optional.of(new RemoteRequestEV3(ip));
        } catch (IOException e) {
            log.error("discovery exception", e);
        }

        return Optional.empty();
    }

    public void unregisterByName(
            final String name
    ) {
        final RemoteRequestEV3 ev3 = DEVICE_MAP.get(name);
        DEVICE_MAP.remove(name);

        // TODO close RegulatedMotor

        ev3.disConnect();
    }

}
