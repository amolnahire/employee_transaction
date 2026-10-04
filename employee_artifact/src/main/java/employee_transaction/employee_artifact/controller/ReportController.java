package employee_transaction.employee_artifact.controller;

import employee_transaction.employee_artifact.dto.DailySummaryResponse;
import employee_transaction.employee_artifact.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/daily")
    public List<DailySummaryResponse> getDailyReports() {

        return reportService.getAllReports();
    }

    @GetMapping("/daily/{date}")
    public DailySummaryResponse getDailyReport(
            @PathVariable LocalDate date) {

        return reportService.getByDate(date);
    }
}
