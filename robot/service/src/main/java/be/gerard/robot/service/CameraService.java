package be.gerard.robot.service;

import be.gerard.robot.model.Camera;
import be.gerard.robot.model.MediaList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class CameraService {

    private static final Map<String, WebClient> CAMERA_IP_MAP = new ConcurrentHashMap<>();

    static Optional<WebClient> findByName(
            final String name
    ) {
        return Optional.ofNullable(CAMERA_IP_MAP.get(name));
    }

    public void registerByName(
            final String name,
            final String ip
    ) {
        CAMERA_IP_MAP.computeIfAbsent(
                name,
                key -> WebClient.create(String.format("http://%s/gp", ip))
        );
    }

    public void changeDefaultBootMode(
            final String name,
            final Camera.Mode mode
    ) {
        findByName(name).ifPresent(webClient -> webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/gpControl/setting/53/{mode}")
                        .build(Map.of(
                                "mode", mode.getCode()
                        ))
                )
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block()
        );
    }

    public void changeMode(
            final String name,
            final Camera.Mode mode
    ) {
        findByName(name).ifPresent(webClient -> webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/gpControl/command/mode")
                        .queryParam("p", mode.getCode())
                        .build()
                )
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block()
        );
    }

    public void changeSubMode(
            final String name,
            final Camera.SubMode subMode
    ) {
        findByName(name).ifPresent(webClient -> webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/gpControl/command/sub_mode")
                        .queryParam("mode", subMode.getMode().getCode())
                        .queryParam("sub_mode", subMode.getSubCode())
                        .build()
                )
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block()
        );
    }

    public void shutter(
            final String name,
            final Camera.Shutter.Action action
    ) {
        findByName(name).ifPresent(webClient -> webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/gpControl/command/shutter")
                        .queryParam("p", action.getCode())
                        .build()
                )
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block()
        );
    }

    public void sleep(
            final String name
    ) {
        findByName(name).ifPresent(webClient -> webClient.get()
                .uri("/gpControl/command/system/sleep")
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block()
        );
    }

    public void pair(
            final String name
    ) {
        findByName(name).ifPresent(webClient -> webClient.get()
                .uri("/gpControl/command/wireless/pair/complete?success=1&deviceName=DESKTOP")
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block()
        );
    }

    public void changeSetting(
            final String name,
            final Camera.Setting setting
    ) {
        findByName(name).ifPresent(webClient -> webClient.get()
                .uri(setting.toUrl())
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block()
        );
    }

    public MediaList getMediaList(
            final String name
    ) {
        return findByName(name).map(webClient -> webClient.get()
                .uri("/gpMediaList")
                .exchange()
                .block()
                .bodyToMono(MediaList.class)
                .block()
        )
                .orElseThrow(() -> new IllegalArgumentException("camera not found"));
    }

    public byte[] getThumbnail(
            final String name,
            final String d,
            final String n
    ) {
        return findByName(name).map(webClient -> webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/gpMediaMetadata")
                        .queryParam("p", String.format(
                                "%s/%s",
                                d,
                                n
                        ))
                        .build()
                )
                .accept(MediaType.ALL)
                .exchange()
                .block()
                .bodyToMono(byte[].class)
                .block()
        )
                .orElseThrow(() -> new IllegalArgumentException("camera not found"));
    }

}
