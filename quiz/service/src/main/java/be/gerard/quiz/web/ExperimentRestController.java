package be.gerard.quiz.web;

import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicLong;

/**
 * ExperimentRestController
 *
 * @author bartgerard
 * @version v0.0.1
 */
@RestController
@RequestMapping("experiments")
public class ExperimentRestController {

    // https://projectreactor.io/docs/core/milestone/reference/#processor-overview

    //private final FluxProcessor<String, String> processor = DirectProcessor.<String>create().serialize();
    private final DirectProcessor<String> processor = DirectProcessor.<String>create();
    private final FluxSink<String> sink = processor.sink();
    private final AtomicLong counter = new AtomicLong();

    @GetMapping(path = "stream-flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamFlux() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(sequence -> "Flux - " + LocalTime.now().toString());
    }

    @GetMapping("stream-sse")
    public Flux<ServerSentEvent<String>> streamEvents() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(sequence -> ServerSentEvent.<String>builder()
                        .id(String.valueOf(sequence))
                        .event("message")
                        .data("SSE - " + LocalTime.now().toString())
                        .build()
                );
    }

    @GetMapping("stream-sse-emitter")
    public Flux<ServerSentEvent<String>> streamSseEmitter() {
        return processor.map(e -> ServerSentEvent.builder(e).build());
    }

    @GetMapping("send")
    private void test() {
        sink.next("Hello World #" + counter.getAndIncrement());
    }

}
