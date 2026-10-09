package employee_transaction.employee_artifact.service;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BatchService {

    private final JobLauncher jobLauncher;
    private final Job transactionProcessingJob;

    public String runBatch() {
        try {
            jobLauncher.run(
                    transactionProcessingJob,
                    new JobParametersBuilder()
                            .addLong("run.id", System.currentTimeMillis())
                            .toJobParameters()
            );
            return "Batch job started successfully";
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to run transaction batch job", ex);
        }
    }
}
