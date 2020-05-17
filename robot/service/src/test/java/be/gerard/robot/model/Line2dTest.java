package be.gerard.robot.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class Line2dTest {

    @Test
    public void test_line_intersections() {
        final var l0 = Point2d.of(1, 0).withDirection(Vector.of(1, 0));
        final var l45 = Point2d.of(1, 1).withDirection(Vector.of(1, 1));
        final var l90 = Point2d.of(0, 1).withDirection(Vector.of(0, 1));

        final var y_is_x_plus_1 = Point2d.of(0, 1).withDirection(Vector.of(1, 1));
        final var y_is_2 = Point2d.of(0, 2).withDirection(Vector.of(1, 0));

        Assertions.assertThat(Line2d.intersect(l0, l45)).isEqualTo(Optional.of(Point2d.of(0, 0)));
        Assertions.assertThat(Line2d.intersect(l0, l90)).isEqualTo(Optional.of(Point2d.of(0, 0)));
        Assertions.assertThat(Line2d.intersect(l45, y_is_x_plus_1)).isEqualTo(Optional.empty());
        Assertions.assertThat(Line2d.intersect(y_is_2, y_is_x_plus_1)).isEqualTo(Optional.of(Point2d.of(1, 2)));
    }

}
