package employee_transaction.employee_artifact.service;


import employee_transaction.employee_artifact.dto.DailySummaryResponse;
import employee_transaction.employee_artifact.entity.DailySummary;
import employee_transaction.employee_artifact.repository.DailySummaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final DailySummaryRepository repository;

    public List<DailySummaryResponse> getAllReports() {

        return repository.findAll()
                .stream()
                .map(DailySummaryResponse::fromEntity)
                .toList();
    }

    public DailySummaryResponse getByDate(LocalDate date) {

        DailySummary summary = repository
                .findByReportDate(date)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Daily report not found for " + date
                        )
                );

        return DailySummaryResponse.fromEntity(summary);
    }
}