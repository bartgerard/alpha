package be.gerard.quiz.web;

import be.gerard.quiz.model.Team;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * TeamRestController
 *
 * @author bartgerard
 * @version v0.0.1
 */
@RestController
@RequestMapping("teams")
public class TeamRestController {

    @GetMapping("{teamId}")
    private Mono<Team> getTeamById(
            @PathVariable("teamId") final String teamId
    ) {
        return Mono.just(new Team("a", "b"));
    }

    @GetMapping
    private Flux<Team> allTeams() {
        final List<Team> teams = IntStream.range(0, 100)
                .mapToObj(String::valueOf)
                .map(i -> new Team(i, i))
                .collect(Collectors.toList());

        return Flux.fromIterable(teams);
    }

}
