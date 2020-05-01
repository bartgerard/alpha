package be.gerard.robot;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.mapping.PatternMatchingCompositeLineMapper;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.util.Map;

@Configuration
@EnableAutoConfiguration
@EnableBatchProcessing
@RequiredArgsConstructor
public class RobotJobConfig {

    private final StepBuilderFactory stepBuilderFactory;
    private final JobBuilderFactory jobBuilderFactory;

    @Bean
    public Job robotJob(
            final Step step1
    ) {
        return jobBuilderFactory.get("robotJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(
            @Qualifier("inputRouteReader") final FlatFileItemReader<Command> itemReader,
            @Qualifier("routeProcessor") final ItemProcessor<Command, String> processor,
            @Qualifier("outputRouteWriter") final FlatFileItemWriter<String> itemWriter
    ) {
        return stepBuilderFactory.get("step1")
                .<Command, String>chunk(1)
                .reader(itemReader)
                .processor(processor)
                .writer(itemWriter)
                .build();
    }

    @Bean
    public LineMapper<Command> robotRouteLineMapper() {
        final var lineMapper = new PatternMatchingCompositeLineMapper<Command>();

        lineMapper.setTokenizers(Map.ofEntries(
                Map.entry("MOV*", movementTokenizer())
        ));

        lineMapper.setFieldSetMappers(Map.ofEntries(
                Map.entry("MOV*", movementFieldSetMapper())
        ));

        return lineMapper;
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
    public FieldSetMapper<Command> movementFieldSetMapper() {
        return fieldSet -> new Move(
                fieldSet.readString("type")
        );
    }

    @Bean
    @StepScope
    public FlatFileItemReader<Command> inputRouteReader(
            @Value("#{jobParameters['input.file']}") final String inputPath
    ) {
        return new FlatFileItemReaderBuilder<Command>()
                .name("inputRouteReader")
                .resource(new FileSystemResource(inputPath))
                .lineMapper(robotRouteLineMapper())
                .build();
    }

    @Bean
    public ItemProcessor<Command, String> routeProcessor() {
        return o -> "test";
    }

    @Bean
    @StepScope
    public FlatFileItemWriter<String> outputRouteWriter(
            @Value("#{jobParameters['output.file']}") final String outputPath
    ) {
        return new FlatFileItemWriterBuilder<String>()
                .name("jsonItemWriter")
                .resource(new FileSystemResource(outputPath))
                .lineAggregator(s -> s)
                .build();
    }

    /*
    @Bean
    @StepScope
    public JsonFileItemWriter<String> outputRouteWriter(
            @Value("#{jobParameters['output.file']}") final String outputPath
    ) {
        final JacksonJsonObjectMarshaller<String> marshaller = new JacksonJsonObjectMarshaller<>();
        return new JsonFileItemWriterBuilder<String>()
                .name("jsonItemWriter")
                .resource(new FileSystemResource(outputPath))
                .jsonObjectMarshaller(marshaller)
                .build();
    }
     */

}
