package be.gerard.common.discovery;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * EurekaServerApplication
 *
 * @author bartgerard
 * @version v0.0.1
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

    public static void main(
            final String[] args
    ) {
        new SpringApplicationBuilder(EurekaServerApplication.class).run(args);
    }

}
