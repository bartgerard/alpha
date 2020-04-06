package be.gerard.quiz.event;

import be.gerard.quiz.model.Question;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

/**
 * Quiz
 *
 * @author bartgerard
 * @version v0.0.1
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Quiz {

    public interface Event {}

    @Value
    public static class Started implements Event {

    }

    @Value
    @Builder
    public static class StateChanged implements Event {
        private final int roundId;
        private final int questionId;
        private final Question.State questionState;
    }

}
