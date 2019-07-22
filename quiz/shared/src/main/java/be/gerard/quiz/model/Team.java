package be.gerard.quiz.model;

import lombok.Value;

/**
 * Team
 *
 * @author bartgerard
 * @version v0.0.1
 */
@Value
public class Team {

    private final Id id;
    private final String name;

    public static Id withId(
            final int id
    ) {
        return new Id(id);
    }

    @Value
    public static class Id {
        private int value;

        public Team andName(
                final String name
        ) {
            return new Team(
                    this,
                    name
            );
        }

    }

}
