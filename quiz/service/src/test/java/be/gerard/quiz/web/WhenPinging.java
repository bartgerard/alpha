package be.gerard.quiz.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * WhenPinging
 *
 * @author bartgerard
 * @version v0.0.1
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WhenPinging {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void test() {
        webTestClient.method(HttpMethod.GET)
                .uri("ping")
                .contentType(MediaType.TEXT_PLAIN)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.TEXT_PLAIN)
                .expectBody(String.class)
                .isEqualTo("pong");
    }

}
