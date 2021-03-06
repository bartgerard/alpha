package be.gerard.robot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class RobotApplication {

    public static void main(
            final String[] args
    ) {
        SpringApplication.run(RobotApplication.class, args);
    }

}
