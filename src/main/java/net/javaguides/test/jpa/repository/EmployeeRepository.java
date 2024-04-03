package net.javaguides.test.jpa.repository;

import net.javaguides.test.jpa.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    @Query("select e from Employee e where e.firstName= ?1 and e.lastName=?2")
    public Optional<Employee> findEmployeeByJPQL(String firstName, String lastName);

    @Query("select e from Employee e where e.firstName=:firstName and e.lastName=:lastName")
    public Optional<Employee> findEmployeeByJPQLNamedparam(@Param("firstName") String firstName, @Param("lastName") String lastName);

    //Find by first name and last name using inddex parmeter
    @Query(value = "select e.* from employees e where first_name=?1 and last_name=?2", nativeQuery = true)
    public Employee findEmployeeByFirstNameAndLastNameWithNativeQuery(String firstName, String lastName);

    //Find by first name and last name using named parmeter
    @Query(value = "select e.* from employees e where first_name=:firstName and last_name=:lastName", nativeQuery = true)
    public Employee findEmployeeByFirstNameAndLastNameWithNativeQueryNamedParam(@Param("firstName") String firstName, @Param("lastName") String lastName);
}
