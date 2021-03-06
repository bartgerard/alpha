package be.gerard.empires;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableDiscoveryClient
@EnableWebFlux
public class MegaEmpiresApplication {

    public static void main(
            final String[] args
    ) {
        SpringApplication.run(MegaEmpiresApplication.class, args);
    }

}