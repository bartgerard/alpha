package be.gerard.team;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TeamApplication {

    public static void main(
            final String[] args
    ) {
        SpringApplication.run(TeamApplication.class, args);
    }

}
