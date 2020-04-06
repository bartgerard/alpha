package be.gerard.quiz.web;

import be.gerard.quiz.event.Quiz;
import be.gerard.quiz.model.Question;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * QuizRestController
 *
 * @author bartgerard
 * @version v0.0.1
 */
@RestController
@RequestMapping("quiz")
public class QuizRestController {

    private final DirectProcessor<Quiz.StateChanged> processor = DirectProcessor.create();
    private final FluxSink<Quiz.StateChanged> sink = processor.sink();
    private final AtomicInteger counter = new AtomicInteger();

    @GetMapping("stream-sse-emitter")
    public Flux<ServerSentEvent<Quiz.StateChanged>> streamSseEmitter() {
        return processor.map(data -> ServerSentEvent.builder(data)
                .build()
        );
    }

    @GetMapping("send")
    private void test() {
        sink.next(Quiz.StateChanged.builder()
                .roundId(1)
                .questionId(counter.getAndIncrement())
                .questionState(Question.State.STARTED)
                .build()
        );
    }

}
