package be.gerard.empires.model;

import lombok.Value;

@Value
public class Empire {

    Empire.Id id;
    String label;
    String hexColorCode;

    public static Id withId(
            final int id
    ) {
        return new Id(id);
    }

    @Value
    public static class Id {

        int value;

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
