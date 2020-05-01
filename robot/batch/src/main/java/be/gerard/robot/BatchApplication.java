package be.gerard.robot;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class BatchApplication implements CommandLineRunner {

    private final JobLauncher jobLauncher;
    private final Job job;

    @Override
    public void run(
            final String... args
    ) throws Exception {
        final JobParametersBuilder paramsBuilder = new JobParametersBuilder();
        //paramsBuilder.addString("input.file", "input");

        jobLauncher.run(
                job,
                paramsBuilder.toJobParameters()
        );
    }
}
