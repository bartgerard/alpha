package be.gerard.common.gateway;

import com.netflix.discovery.EurekaClient;
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
            final RouteLocatorBuilder builder,
            final EurekaClient discoveryClient
    ) {
        /*
        spring:
          cloud:
            gateway:
              discovery.locator:
                enabled: true
                lowerCaseServiceId: true
         */
        final String quizServiceUrl = discoveryClient.getNextServerFromEureka("quiz-service", false)
                .getHomePageUrl();
        final String teamServiceUrl = discoveryClient.getNextServerFromEureka("team-service", false)
                .getHomePageUrl();

        return builder.routes()
                //.route(p -> p.path("/get")
                //        .filters(f -> f.addRequestHeader("Hello", "World"))
                //        .uri("http://httpbin.org:80")
                //)
                .route(p -> p.path("/ping")
                        .uri(quizServiceUrl + "ping")
                )
                .route(p -> p.path("/quiz/**")
                        .uri(quizServiceUrl)
                )
                .route(p -> p.path("/teams/**")
                        .uri(teamServiceUrl)
                )
                .build();
    }

}
