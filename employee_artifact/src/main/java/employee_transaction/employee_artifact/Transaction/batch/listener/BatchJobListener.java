package employee_transaction.employee_artifact.Transaction.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BatchJobListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {

        log.info(
                "Transaction batch job started. Execution ID: {}",
                jobExecution.getId()
        );
    }

    @Override
    public void afterJob(JobExecution jobExecution) {

        log.info(
                "Transaction batch job completed. Status: {}",
                jobExecution.getStatus()
        );
    }
}
