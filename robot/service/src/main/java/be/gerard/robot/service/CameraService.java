package be.gerard.robot.service;

import be.gerard.robot.model.Camera;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class CameraService {

    private static final Map<String, WebClient> CAMERA_IP_MAP = new ConcurrentHashMap<>();

    private RestTemplate restTemplate;

    static Optional<WebClient> findByName(
            final String name
    ) {
        return Optional.ofNullable(CAMERA_IP_MAP.get(name));
    }

    public void registerByName(
            final String name,
            final String ip
    ) {
        final var webClient = WebClient.create(String.format("http://%s/gp/gpControl", ip));
        CAMERA_IP_MAP.put(name, webClient);
    }

    public void changeDefaultBootMode(
            final String name,
            final Camera.Mode mode
    ) {
        findByName(name).ifPresent(webClient -> webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/setting/53/{mode}")
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
                        .path("/command/mode")
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
                        .path("/command/sub_mode")
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
                        .path("/command/shutter")
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
                .uri("/command/system/sleep")
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
                .uri("/command/wireless/pair/complete?success=1&deviceName=DESKTOP")
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block()
        );
    }

}
