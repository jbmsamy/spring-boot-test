package net.javaguides.test.jpa.repository;

import net.javaguides.test.jpa.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);
    @Query("select e from Employee e where e.firstName= ?1 and e.lastName=?2")
    public Optional<Employee> findEmployeeByJPQL(String firstName, String lastName);
}
