package employee_transaction.employee_artifact.repository;





import employee_transaction.employee_artifact.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmployeeId(String employeeId);

    Optional<Employee> findByEmail(String email);

    Employee save(Employee existingEmployee);

    void delete(Employee employee);
}
