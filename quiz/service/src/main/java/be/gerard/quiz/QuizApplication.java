package be.gerard.quiz;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.reactive.config.EnableWebFlux;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableDiscoveryClient
@EnableWebFlux
public class QuizApplication {

    public static void main(
            final String[] args
    ) {
        //SpringApplication.run(QuizApplication.class, args);

        final List<Integer> numbers = Arrays.asList(1, 5, 3, 4, 2);

        final List<Integer> square = numbers.stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        System.out.println(square);

        // [5, 4, 3, 2, 1]
    }

}
