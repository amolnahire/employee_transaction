package employee_transaction.employee_artifact.service;


import employee_transaction.employee_artifact.entity.Employee;
import employee_transaction.employee_artifact.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public Employee create(Employee employee) {

        return employeeRepository.save(employee);
    }

    public List<Employee> getAll() {

        return employeeRepository.findAll();
    }

    public Employee getById(Long id) {

        return employeeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Employee not found: " + id
                        )
                );
    }

    public Employee update(Long id, Employee employee) {

        Employee existing = getById(id);

        existing.setEmployeeId(employee.getEmployeeId());
        existing.setName(employee.getName());
        existing.setDepartment(employee.getDepartment());
        existing.setEmail(employee.getEmail());

        return employeeRepository.save(existing);
    }

    public void delete(Long id) {

        employeeRepository.deleteById(id);
    }
}