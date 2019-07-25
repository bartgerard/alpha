package be.gerard.common.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

/**
 * GatewayApplication
 *
 * @author bartgerard
 * @version v0.0.1
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {

    public static void main(
            final String[] args
    ) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator myRoutes(
            final RouteLocatorBuilder builder
    ) {
        // lb:// --> Load Balancing?

        return builder.routes()
                //.route(p -> p.path("/get")
                //        .filters(f -> f.addRequestHeader("Hello", "World"))
                //        .uri("http://httpbin.org:80")
                //)
                .route(p -> p.path("/ping/**")
                        .filters(rw -> rw.rewritePath("/quiz/(?<segment>.*)", "/${segment}"))
                        .uri("lb://QUIZ-SERVICE")
                )
                .route(p -> p.path("/quiz/**")
                        .uri("lb://QUIZ-SERVICE")
                )
                .route(p -> p.path("/teams/**")
                        .uri("lb://TEAM-SERVICE")
                )
                .build();
    }

}