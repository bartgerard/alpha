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

        Assertions.assertThat(Vector.angle(V1, V2).toDegrees()).isEqualTo(90);
        Assertions.assertThat(Vector.angle(V2, V1).toDegrees()).isEqualTo(90);

        Assertions.assertThat(V1.angle2d().toDegrees()).isEqualTo(0);
        Assertions.assertThat(V2.angle2d().toDegrees()).isEqualTo(90);
        Assertions.assertThat(v3.angle2d().toDegrees()).isEqualTo(180);
        Assertions.assertThat(v4.angle2d().toDegrees()).isEqualTo(-90);

        Assertions.assertThat(Vector.angle2d(V1, V2).toDegrees()).isEqualTo(-90);
        Assertions.assertThat(Vector.angle2d(V1, v3).toDegrees()).isEqualTo(-180);
        Assertions.assertThat(Vector.angle2d(v3, V1).toDegrees()).isEqualTo(180);
        Assertions.assertThat(Vector.angle2d(V1, v4).toDegrees()).isEqualTo(90);

        Assertions.assertThat(Vector.angle2d(V1, v225).toDegrees()).isEqualTo(135);
        Assertions.assertThat(Vector.angle2d(V2, v225).toDegrees()).isEqualTo(225);

        Assertions.assertThat(Vector.angle2d(V1, v225).toSmallestAngle().toDegrees()).isEqualTo(135);
        Assertions.assertThat(Vector.angle2d(V2, v225).toSmallestAngle().toDegrees()).isEqualTo(-135);

        Assertions.assertThat(Vector.angle2d(V1, v225).toSmallestAngle().isObtuse()).isTrue();
        Assertions.assertThat(Vector.angle2d(V2, v225).toSmallestAngle().isObtuse()).isTrue();

        Assertions.assertThat(Vector.angle2d(V1, V1).toDegrees()).isEqualTo(0);
    }

    @Test
    public void test_rotate_vector() {
        final var vRot1 = Vector.rotateZ(V1, Math.PI);
        Assertions.assertThat(vRot1.getX()).isEqualTo(-1, PRECISION);
        Assertions.assertThat(vRot1.getY()).isEqualTo(0, PRECISION);

        final var vRot2 = Vector.rotateZ(V1, Math.PI * 0.5);
        Assertions.assertThat(vRot2.getX()).isEqualTo(0, PRECISION);
        Assertions.assertThat(vRot2.getY()).isEqualTo(1, PRECISION);

        final var vRot3 = Vector.rotateZ(V1, -Math.PI * 0.5);
        Assertions.assertThat(vRot3.getX()).isEqualTo(0, PRECISION);
        Assertions.assertThat(vRot3.getY()).isEqualTo(-1, PRECISION);
    }

    @Test
    public void test_pointer_operations() {
        final Point2d p1 = Point2d.of(1, 0);
        final Point2d p2 = Point2d.of(0, 1);

        Assertions.assertThat(p1.min(p2)).isEqualTo(Vector.of(-1, 1));
    }

}
