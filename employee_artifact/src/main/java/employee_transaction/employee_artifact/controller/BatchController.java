package employee_transaction.employee_artifact.controller;

import employee_transaction.employee_artifact.service.BatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/batch")
@RequiredArgsConstructor
public class BatchController {

    private final BatchService batchService;

    @PostMapping("/run")
    public String runBatch() {
        return batchService.runBatch();
    }
}