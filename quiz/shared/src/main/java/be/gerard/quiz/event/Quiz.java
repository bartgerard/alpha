package be.gerard.quiz.event;

import lombok.AccessLevel;
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

}
