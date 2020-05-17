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

        final var v225 = Vector.of(-1, -1);

        Assertions.assertThat(Vector.magnitude(v45)).isEqualTo(1.41, PRECISION);

        Assertions.assertThat(Vector.angle(V1, V2).getDegrees()).isEqualTo(90);
        Assertions.assertThat(Vector.angle(V2, V1).getDegrees()).isEqualTo(90);

        Assertions.assertThat(V1.angle2d().getDegrees()).isEqualTo(0);
        Assertions.assertThat(V2.angle2d().getDegrees()).isEqualTo(90);
        Assertions.assertThat(v3.angle2d().getDegrees()).isEqualTo(180);
        Assertions.assertThat(v4.angle2d().getDegrees()).isEqualTo(-90);

        Assertions.assertThat(Vector.angle2d(V1, V2).getDegrees()).isEqualTo(-90);
        Assertions.assertThat(Vector.angle2d(V1, v3).getDegrees()).isEqualTo(-180);
        Assertions.assertThat(Vector.angle2d(v3, V1).getDegrees()).isEqualTo(180);
        Assertions.assertThat(Vector.angle2d(V1, v4).getDegrees()).isEqualTo(90);

        Assertions.assertThat(Vector.angle2d(V1, v225).getDegrees()).isEqualTo(135);
        Assertions.assertThat(Vector.angle2d(V2, v225).getDegrees()).isEqualTo(225);

        Assertions.assertThat(Vector.angle2d(V1, v225).toSmallestAngle().getDegrees()).isEqualTo(135);
        Assertions.assertThat(Vector.angle2d(V2, v225).toSmallestAngle().getDegrees()).isEqualTo(-135);

        Assertions.assertThat(Vector.angle2d(V1, v225).toSmallestAngle().isObtuse()).isTrue();
        Assertions.assertThat(Vector.angle2d(V2, v225).toSmallestAngle().isObtuse()).isTrue();
    }

    @Test
    public void test_pointer_operations() {
        final Point2d p1 = Point2d.of(1, 0);
        final Point2d p2 = Point2d.of(0, 1);

        Assertions.assertThat(p1.min(p2)).isEqualTo(Vector.of(-1, 1));
    }

}
