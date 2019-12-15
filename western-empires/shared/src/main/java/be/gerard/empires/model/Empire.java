package be.gerard.empires.model;

import lombok.Value;

@Value
public class Empire {

    private final Empire.Id id;
    private final String label;
    private final String hexColorCode;

    public static Id withId(
            final int id
    ) {
        return new Id(id);
    }

    @Value
    public static class Id {

        private int value;

        public Empire andLabelAndColorCode(
                final String label,
                final String hexColorCode
        ) {
            return new Empire(
                    this,
                    label,
                    hexColorCode
            );
        }

    }

}
