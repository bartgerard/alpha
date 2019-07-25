package be.gerard.quiz.config;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.resource.PathResourceResolver;
import reactor.core.publisher.Mono;

import java.io.IOException;

/**
 * WebFluxConfig
 *
 * @author bartgerard
 * @version v0.0.1
 */
//@Configuration
@EnableWebFlux
public class WebFluxConfig implements WebFluxConfigurer {

    @Override
    public void addResourceHandlers(
            final ResourceHandlerRegistry registry
    ) {
        registry.addResourceHandler("/**/*")
                .addResourceLocations("classpath:/static/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Mono<Resource> getResource(
                            final String resourcePath,
                            final Resource location
                    ) {
                        try {
                            final Resource requestedResource = location.createRelative(resourcePath);

                            if (requestedResource.exists() && requestedResource.isReadable()) {
                                return Mono.just(requestedResource);
                            }

                            return Mono.just(new ClassPathResource("/static/index.html"));
                        } catch (IOException e) {
                            return Mono.error(e);
                        }
                    }
                });
    }

}
