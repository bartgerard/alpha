package be.gerard.quiz.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * PingRestController
 *
 * @author bartgerard
 * @version v0.0.1
 */
@RestController
public class PingRestController {

    @GetMapping("ping")
    public Mono<String> ping() {
        return Mono.just("pong");
    }

}
