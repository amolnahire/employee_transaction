package employee_transaction.employee_artifact.controller;




import employee_transaction.employee_artifact.entity.Employee;
import employee_transaction.employee_artifact.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(
            @RequestBody Employee employee) {

        return employeeService.create(employee);
    }

    @GetMapping
    public List<Employee> getAll() {

        return employeeService.getAll();
    }

    @GetMapping("/{id}")
    public Employee getById(
            @PathVariable Long id) {

        return employeeService.getById(id);
    }

    @PutMapping("/{id}")
    public Employee update(
            @PathVariable Long id,
            @RequestBody Employee employee) {

        return employeeService.update(id, employee);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id) {

        employeeService.delete(id);
    }
}