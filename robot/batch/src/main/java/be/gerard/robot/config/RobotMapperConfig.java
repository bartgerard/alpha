package be.gerard.robot.config;

import be.gerard.robot.model.Controls;
import be.gerard.robot.model.PilotConfiguration;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.mapping.PatternMatchingCompositeLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class RobotMapperConfig {

    @Bean
    public LineMapper<Controls.Control> robotRouteLineMapper() {
        final var lineMapper = new PatternMatchingCompositeLineMapper<Controls.Control>();

        final var defaultLineTokenizer = new DelimitedLineTokenizer();

        lineMapper.setTokenizers(Map.ofEntries(
                Map.entry("REG*", registrationTokenizer()),
                Map.entry("MOV*", movementTokenizer()),
                Map.entry("*", defaultLineTokenizer)
        ));

        lineMapper.setFieldSetMappers(Map.ofEntries(
                Map.entry("REG*", registrationFieldSetMapper()),
                Map.entry("MOV*", movementFieldSetMapper())
        ));

        return lineMapper;
    }

    @Bean
    public LineTokenizer registrationTokenizer() {
        final var tokenizer = new DelimitedLineTokenizer();

        tokenizer.setDelimiter(",");
        tokenizer.setNames(
                "type",
                "name",
                "pilotId",
                "wheelDiameter",
                "trackWidth",
                "leftMotor",
                "rightMotor"
        );

        return tokenizer;
    }

    @Bean
    public LineTokenizer movementTokenizer() {
        final var tokenizer = new FixedLengthTokenizer();

        tokenizer.setColumns(
                new Range(1, 3)
        );
        tokenizer.setNames(
                "type"
        );

        return tokenizer;
    }

    @Bean
    public FieldSetMapper<Controls.Control> registrationFieldSetMapper() {
        return fieldSet -> new Controls.Register(
                fieldSet.readString("name"),
                fieldSet.readString("pilotId"),
                PilotConfiguration.builder()
                        .wheelDiameter(fieldSet.readInt("wheelDiameter"))
                        .trackWidth(fieldSet.readInt("trackWidth"))
                        .leftMotor(fieldSet.readString("leftMotor"))
                        .rightMotor(fieldSet.readString("rightMotor"))
                        .build()
        );
    }

    @Bean
    public FieldSetMapper<Controls.Control> movementFieldSetMapper() {
        return fieldSet -> new Controls.TravelArc(
                0,
                0
        );
    }

}
