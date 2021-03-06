package be.gerard.quiz.model;

import lombok.Value;

@Value
public class Question {

    private final String value;

    public enum State {
        PENDING,
        ASKED,
        STARTED,
        ENDED
    }

}
