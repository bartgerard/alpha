package be.gerard.team.web;

import be.gerard.team.model.Team;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
            @PathVariable("teamId") final int teamId
    ) {
        return Mono.just(
                Team.withId(1).andName("exQuizIt")
        );
    }

    @GetMapping
    private Flux<Team> allTeams() {
        return Flux.fromStream(IntStream.range(0, 100)
                .mapToObj(i -> Team.withId(i).andName(Integer.toString(i)))
        );
    }

}
