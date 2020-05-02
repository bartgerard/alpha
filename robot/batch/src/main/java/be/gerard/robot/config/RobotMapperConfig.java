package be.gerard.robot.config;

import be.gerard.robot.model.PilotConfiguration;
import be.gerard.robot.model.Timelapse;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.mapping.PatternMatchingCompositeLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class RobotMapperConfig {

    public static final String
            TYPE = "type",
            NAME = "name",
            PILOT_ID = "pilotId",
            WHEEL_DIAMETER = "wheelDiameter",
            TRACK_WIDTH = "trackWidth",
            LEFT_MOTOR = "leftMotor",
            RIGHT_MOTOR = "rightMotor",
            RADIUS = "radius",
            ANGLE = "angle",
            DISTANCE = "distance",
            IP = "ip";

    @Bean
    public LineMapper<Timelapse.Control> robotRouteLineMapper() {
        final var lineMapper = new PatternMatchingCompositeLineMapper<Timelapse.Control>();

        final var defaultLineTokenizer = new DelimitedLineTokenizer();

        lineMapper.setTokenizers(Map.ofEntries(
                Map.entry("CAM*", camRegistrationTokenizer()),
                Map.entry("REG*", registrationTokenizer()),
                Map.entry("ARC*", arcMovementTokenizer()),
                Map.entry("MOV*", forwardMovementTokenizer()),
                Map.entry("ROT*", rotateMovementTokenizer()),
                Map.entry("DRG*", deRegistrationTokenizer()),
                Map.entry("*", defaultLineTokenizer)
        ));

        lineMapper.setFieldSetMappers(Map.ofEntries(
                Map.entry("CAM*", cameraRegistrationFieldSetMapper()),
                Map.entry("REG*", registrationFieldSetMapper()),
                Map.entry("ARC*", arcMovementFieldSetMapper()),
                Map.entry("MOV*", forwardMovementFieldSetMapper()),
                Map.entry("ROT*", rotateMovementFieldSetMapper()),
                Map.entry("DRG*", deRegistrationFieldSetMapper())
        ));

        return lineMapper;
    }

    @Bean
    public LineTokenizer camRegistrationTokenizer() {
        final var tokenizer = new DelimitedLineTokenizer();

        tokenizer.setDelimiter(",");
        tokenizer.setNames(
                TYPE,
                NAME,
                IP
        );

        return tokenizer;
    }

    @Bean
    public LineTokenizer registrationTokenizer() {
        final var tokenizer = new DelimitedLineTokenizer();

        tokenizer.setDelimiter(",");
        tokenizer.setNames(
                TYPE,
                NAME,
                PILOT_ID,
                WHEEL_DIAMETER,
                TRACK_WIDTH,
                LEFT_MOTOR,
                RIGHT_MOTOR
        );

        return tokenizer;
    }

    @Bean
    public LineTokenizer arcMovementTokenizer() {
        final var tokenizer = new DelimitedLineTokenizer();

        tokenizer.setDelimiter(",");
        tokenizer.setNames(
                TYPE,
                NAME,
                PILOT_ID,
                RADIUS,
                ANGLE
        );

        return tokenizer;
    }

    @Bean
    public LineTokenizer forwardMovementTokenizer() {
        final var tokenizer = new DelimitedLineTokenizer();

        tokenizer.setDelimiter(",");
        tokenizer.setNames(
                TYPE,
                NAME,
                PILOT_ID,
                DISTANCE
        );

        return tokenizer;
    }

    @Bean
    public LineTokenizer rotateMovementTokenizer() {
        final var tokenizer = new DelimitedLineTokenizer();

        tokenizer.setDelimiter(",");
        tokenizer.setNames(
                TYPE,
                NAME,
                PILOT_ID,
                ANGLE
        );

        return tokenizer;
    }

    @Bean
    public LineTokenizer deRegistrationTokenizer() {
        final var tokenizer = new DelimitedLineTokenizer();

        tokenizer.setDelimiter(",");
        tokenizer.setNames(
                TYPE,
                NAME
        );

        return tokenizer;
    }

    @Bean
    public FieldSetMapper<Timelapse.Control> cameraRegistrationFieldSetMapper() {
        return fieldSet -> Timelapse.RegisterCamera.builder()
                .name(fieldSet.readString(NAME))
                .ip(fieldSet.readString(IP))
                .build();
    }

    @Bean
    public FieldSetMapper<Timelapse.Control> registrationFieldSetMapper() {
        return fieldSet -> Timelapse.RegisterEv3.builder()
                .name(fieldSet.readString(NAME))
                .pilotId(fieldSet.readString(PILOT_ID))
                .pilotConfiguration(PilotConfiguration.builder()
                        .wheelDiameter(fieldSet.readInt(WHEEL_DIAMETER))
                        .trackWidth(fieldSet.readInt(TRACK_WIDTH))
                        .leftMotor(fieldSet.readString(LEFT_MOTOR))
                        .rightMotor(fieldSet.readString(RIGHT_MOTOR))
                        .build()
                )
                .build();
    }

    @Bean
    public FieldSetMapper<Timelapse.Control> arcMovementFieldSetMapper() {
        return fieldSet -> Timelapse.TravelArc.builder()
                .name(fieldSet.readString(NAME))
                .pilotId(fieldSet.readString(PILOT_ID))
                .radius(fieldSet.readDouble(RADIUS))
                .angle(fieldSet.readDouble(ANGLE))
                .build();
    }

    @Bean
    public FieldSetMapper<Timelapse.Control> forwardMovementFieldSetMapper() {
        return fieldSet -> Timelapse.MoveForward.builder()
                .name(fieldSet.readString(NAME))
                .pilotId(fieldSet.readString(PILOT_ID))
                .distance(fieldSet.readDouble(DISTANCE))
                .build();
    }

    @Bean
    public FieldSetMapper<Timelapse.Control> rotateMovementFieldSetMapper() {
        return fieldSet -> Timelapse.Rotate.builder()
                .name(fieldSet.readString(NAME))
                .pilotId(fieldSet.readString(PILOT_ID))
                .angle(fieldSet.readDouble(ANGLE))
                .build();
    }

    @Bean
    public FieldSetMapper<Timelapse.Control> deRegistrationFieldSetMapper() {
        return fieldSet -> Timelapse.Deregister.builder()
                .name(fieldSet.readString(NAME))
                .build();
    }

}
