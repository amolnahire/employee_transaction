package employee_transaction.employee_artifact.config;

import employee_transaction.employee_artifact.Transaction.TransactionItemProcessor;
import employee_transaction.employee_artifact.Transaction.batch.listener.BatchJobListener;
import employee_transaction.employee_artifact.entity.DailySummary;
import employee_transaction.employee_artifact.entity.Transaction;
import employee_transaction.employee_artifact.repository.DailySummaryRepository;
import employee_transaction.employee_artifact.repository.TransactionRepository;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class BatchConfig {

    @Bean
    public Job transactionProcessingJob(
            JobRepository jobRepository,
            Step transactionProcessingStep,
            BatchJobListener listener) {

        return new JobBuilder("transactionProcessingJob", jobRepository)
                .listener(listener)
                .start(transactionProcessingStep)
                .build();
    }

    @Bean
    public Step transactionProcessingStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            JpaPagingItemReader<Transaction> transactionReader,
            TransactionItemProcessor transactionItemProcessor,
            ItemWriter<Transaction> transactionWriter) {

        return new StepBuilder("transactionProcessingStep", jobRepository)
                .<Transaction, Transaction>chunk(10, transactionManager)
                .reader(transactionReader)
                .processor(transactionItemProcessor)
                .writer(transactionWriter)
                .build();
    }

    @Bean
    public JpaPagingItemReader<Transaction> transactionReader(
            EntityManagerFactory entityManagerFactory) {

        JpaPagingItemReader<Transaction> reader = new JpaPagingItemReader<>();
        reader.setName("transactionReader");
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setQueryString("SELECT t FROM Transaction t WHERE t.status = :status");
        reader.setParameterValues(Map.of("status", "PENDING"));
        reader.setPageSize(10);
        reader.setSaveState(false);
        return reader;
    }

    @Bean
    public ItemWriter<Transaction> transactionWriter(
            TransactionRepository transactionRepository,
            DailySummaryRepository dailySummaryRepository) {

        return items -> {
            if (items == null || items.isEmpty()) {
                return;
            }

            List<Transaction> processedTransactions = new ArrayList<>();
            for (Transaction transaction : items) {
                processedTransactions.add(transactionRepository.save(transaction));
            }

            Map<LocalDate, List<Transaction>> byDate = new HashMap<>();
            for (Transaction transaction : processedTransactions) {
                byDate.computeIfAbsent(transaction.getTransactionDate(), key -> new ArrayList<>())
                        .add(transaction);
            }

            for (Map.Entry<LocalDate, List<Transaction>> entry : byDate.entrySet()) {
                LocalDate reportDate = entry.getKey();
                List<Transaction> transactionsForDate = transactionRepository.findByTransactionDate(reportDate);

                long validTransactions = transactionsForDate.stream()
                        .filter(transaction -> "VALID".equals(transaction.getStatus()))
                        .count();
                long invalidTransactions = transactionsForDate.stream()
                        .filter(transaction -> "INVALID".equals(transaction.getStatus()))
                        .count();
                BigDecimal totalAmount = transactionsForDate.stream()
                        .map(Transaction::getAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                DailySummary summary = dailySummaryRepository.findByReportDate(reportDate)
                        .orElse(new DailySummary());

                summary.setReportDate(reportDate);
                summary.setTotalTransactions((long) transactionsForDate.size());
                summary.setValidTransactions(validTransactions);
                summary.setInvalidTransactions(invalidTransactions);
                summary.setTotalAmount(totalAmount);

                dailySummaryRepository.save(summary);
            }
        };
    }
}
