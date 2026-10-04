package employee_transaction.employee_artifact.repository;


import employee_transaction.employee_artifact.entity.DailySummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DailySummaryRepository
        extends JpaRepository<DailySummary, Long> {

    Optional<DailySummary> findByReportDate(LocalDate reportDate);
}