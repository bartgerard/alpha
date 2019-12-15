package be.gerard.empires.model;

import lombok.Value;

@Value
public class Player {

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

        public Player andName(
                final String name
        ) {
            return new Player(
                    this,
                    name
            );
        }

    }

}
