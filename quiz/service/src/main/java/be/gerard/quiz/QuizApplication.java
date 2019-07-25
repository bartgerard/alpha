package be.gerard.quiz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableDiscoveryClient
@EnableWebFlux
public class QuizApplication {

    public static void main(
            final String[] args
    ) {
        SpringApplication.run(QuizApplication.class, args);
    }

}
