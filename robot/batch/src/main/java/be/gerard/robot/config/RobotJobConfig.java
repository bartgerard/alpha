package be.gerard.robot.config;

import be.gerard.robot.model.Timelapse;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.FileSystemResource;

@Configuration
@ComponentScan({
        "be.gerard.robot.service"
})
@EnableAutoConfiguration
@EnableBatchProcessing
@RequiredArgsConstructor
@Import({
        RobotMapperConfig.class
})
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
            @Qualifier("inputRouteReader") final FlatFileItemReader<Timelapse.Control> itemReader,
            @Qualifier("routeProcessor") final ItemProcessor<Timelapse.Control, String> processor,
            @Qualifier("outputRouteWriter") final FlatFileItemWriter<String> itemWriter
    ) {
        return stepBuilderFactory.get("step1")
                .<Timelapse.Control, String>chunk(10)
                .reader(itemReader)
                .processor(processor)
                .writer(itemWriter)
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<Timelapse.Control> inputRouteReader(
            @Value("#{jobParameters['input.file']}") final String inputPath,
            @Qualifier("robotRouteLineMapper") final LineMapper<Timelapse.Control> robotRouteLineMapper

    ) {
        return new FlatFileItemReaderBuilder<Timelapse.Control>()
                .name("inputRouteReader")
                .resource(new FileSystemResource(inputPath))
                .lineMapper(robotRouteLineMapper)
                .build();
    }

    @Bean
    public ItemProcessor<Timelapse.Control, String> routeProcessor(
            final ApplicationEventPublisher eventPublisher
    ) {
        return event -> {
            try {
                eventPublisher.publishEvent(event);
                return event + "\n> DONE";
            } catch (Exception e) {
                return event + "\n> FAILED";
            }
        };
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
