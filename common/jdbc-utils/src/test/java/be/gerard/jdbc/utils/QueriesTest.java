package be.gerard.jdbc.utils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class QueriesTest {

    private static final String FILENAME = "queries/query.sql";
    private static final String EXPECTED_VALUE = "select 1\nfrom dual";

    @Test
    public void load_file() {
        // given

        // when
        final String query = Queries.load(FILENAME);

        // then
        Assertions.assertThat(query).isEqualTo(EXPECTED_VALUE);
    }

    @Test
    public void load_file_from_a_specific_resource_folder() {
        // given

        // when
        final String query = Queries.loadFromClassPath(QueriesTest.class, FILENAME);

        // then
        Assertions.assertThat(query).isEqualTo(EXPECTED_VALUE);
    }

    @Test
    public void load_non_existing_file() {
        // given
        final String nonExistingFileName = "gibberish.sql";

        // when
        final IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Queries.load(nonExistingFileName)
        );

        // then
        Assertions.assertThat(exception.getMessage()).isEqualTo("filename not found [filename=gibberish.sql]");
    }

}
