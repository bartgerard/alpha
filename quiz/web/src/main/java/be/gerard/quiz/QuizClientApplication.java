package be.gerard.quiz;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * QuizClientApplication
 *
 * @author bartgerard
 * @version v0.0.1
 */
@SpringBootApplication
@EnableDiscoveryClient
public class QuizClientApplication {

    public static void main(
            final String[] args
    ) {
        new SpringApplicationBuilder(QuizClientApplication.class).run(args);
    }

}
