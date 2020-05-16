package be.gerard.robot.model;

import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

public class VectorTest {

    public static final Offset<Double> PRECISION = Offset.offset(0.01);
    public static final Vector V1 = Vector.of(1, 0);
    public static final Vector V2 = Vector.of(0, 1);

    @Test
    public void test_vector_operations() {
        Assertions.assertThat(Vector.magnitude(V1)).isEqualTo(1);

        Assertions.assertThat(Vector.magnitude(V2)).isEqualTo(1);

        final var v3 = Vector.of(-1, 0);

        final var v4 = Vector.of(0, -1);

        final var v45 = Vector.of(1, 1);

        Assertions.assertThat(Vector.magnitude(v45)).isEqualTo(1.41, PRECISION);

        Assertions.assertThat(Math.toDegrees(Vector.angle(V1, V2))).isEqualTo(90);
        Assertions.assertThat(Math.toDegrees(Vector.angle(V2, V1))).isEqualTo(90);

        Assertions.assertThat(Math.toDegrees(V1.angle2d())).isEqualTo(0);
        Assertions.assertThat(Math.toDegrees(V2.angle2d())).isEqualTo(90);
        Assertions.assertThat(Math.toDegrees(v3.angle2d())).isEqualTo(180);
        Assertions.assertThat(Math.toDegrees(v4.angle2d())).isEqualTo(-90);

        Assertions.assertThat(Math.toDegrees(Vector.angle2d(V1, V2))).isEqualTo(-90);
        Assertions.assertThat(Math.toDegrees(Vector.angle2d(V1, v3))).isEqualTo(-180);
        Assertions.assertThat(Math.toDegrees(Vector.angle2d(v3, V1))).isEqualTo(180);
        Assertions.assertThat(Math.toDegrees(Vector.angle2d(V1, v4))).isEqualTo(90);

    }

    @Test
    public void test_pointer_operations() {
        final Point2d p1 = Point2d.of(1, 0);
        final Point2d p2 = Point2d.of(0, 1);

        Assertions.assertThat(p1.min(p2)).isEqualTo(Vector.of(-1, 1));
    }

}
