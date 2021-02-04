package be.gerard.robot;

import be.gerard.robot.config.RobotJobConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.AssertFile;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBatchTest
@ContextConfiguration(classes = {
        RobotJobConfig.class
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
public class BatchTest {

    private static final String INPUT_FILE = "src/test/resources/batch/input.txt";
    private static final String OUTPUT_FILE = "src/test/resources/batch/output.txt";
    private static final String OUTPUT_FILE_EXPECTED = "src/test/resources/batch/output-expected.txt";

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @AfterEach
    public void cleanUp() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    private JobParameters defaultJobParameters() {
        final JobParametersBuilder paramsBuilder = new JobParametersBuilder();
        paramsBuilder.addString("input.file", INPUT_FILE);
        paramsBuilder.addString("output.file", OUTPUT_FILE);
        return paramsBuilder.toJobParameters();
    }

    @Test
    public void givenReferenceOutput_whenJobExecuted_thenSuccess() throws Exception {
        // given
        final FileSystemResource expectedResult = new FileSystemResource(OUTPUT_FILE_EXPECTED);
        final FileSystemResource actualResult = new FileSystemResource(OUTPUT_FILE);

        // when
        final JobExecution jobExecution = jobLauncherTestUtils.launchJob(defaultJobParameters());
        final JobInstance actualJobInstance = jobExecution.getJobInstance();
        final ExitStatus actualJobExitStatus = jobExecution.getExitStatus();

        // then
        Assertions.assertThat(actualJobInstance.getJobName()).isEqualTo("robotJob");
        Assertions.assertThat(actualJobExitStatus.getExitCode()).isEqualTo("COMPLETED");
        AssertFile.assertFileEquals(expectedResult, actualResult);
    }

}
