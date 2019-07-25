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
public class EurekaServer {

    public static void main(
            final String[] args
    ) {
        new SpringApplicationBuilder(EurekaServer.class).run(args);
    }

}
