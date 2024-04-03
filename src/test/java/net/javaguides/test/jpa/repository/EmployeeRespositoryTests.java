package net.javaguides.test.jpa.repository;

import net.javaguides.test.jpa.model.Employee;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class EmployeeRespositoryTests {

    @Autowired
    private EmployeeRepository employeeRepsoitory;

    private Employee employee;
    @BeforeEach
    public void setUp() {
        employee = testEmployeeData();
    }
    private Employee testEmployeeData() {
        Employee employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Fadatre")
                .email("ramesh@gmail.com")
                .build();
        return employee;
    }
    //Junit test for employee save operation
    @DisplayName("Junit test for employee save operation")
    @Test
    public void givenEmployeeObject_whenSave_thenReturnEmployeeObject()  {
        //given
        /*Employee employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Fadatre")
                .email("ramesh@gmail.com")
                .build();
        *///when
        Employee savedEmployee = employeeRepsoitory.save(employee);

        //then
        Assertions.assertThat(savedEmployee).isNotNull();
        Assertions.assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    @Test
    public void givenEmployeeList_whenFindAll_thenEmployeeList() {
        //begin
        /*Employee employee1 = Employee.builder()
                .firstName("Ramesh1")
                .lastName("Fadatre1")
                .email("ramesh1@gmail.com")
                .build();*/
        Employee employee2 = Employee.builder()
                .firstName("Ramesh2")
                .lastName("Fadatre2")
                .email("ramesh2@gmail.com")
                .build();
        //when
        employeeRepsoitory.save(employee);
        employeeRepsoitory.save(employee2);
        List<Employee> employeeList = employeeRepsoitory.findAll();
        //then
        Assertions.assertThat(employeeList).isNotNull();
        Assertions.assertThat(employeeList.size()).isEqualTo(2);
    }

    @DisplayName("Test case for find by id")
    @Test
    public void givenEmployeeeObject_whenFindById_thenReturnEmployeeObject() {
        // given
        /*Employee employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Fadatre")
                .email("ramesh@gmail.com")
                .build();*/
        employeeRepsoitory.save(employee);
        //when
        Employee employeeDB = employeeRepsoitory.findById(employee.getId()).get();
        //then
        Assertions.assertThat(employeeDB.getId()).isNotNull();
    }

    @DisplayName("Junit Test for employee find by email")
    @Test
    public void givenEmployeeObject_whenFindByEmail_thenReturnEmployeeObject() {
        // given
        //Employee employee = testEmployeeData();
        employeeRepsoitory.save(employee);
        //when
        Employee employeeDB = employeeRepsoitory.findByEmail("ramesh@gmail.com").get();
        //then
        Assertions.assertThat(employeeDB).isNotNull();
        Assertions.assertThat(employeeDB.getEmail())
                .isNotNull().isEqualTo("ramesh@gmail.com");
    }

    @DisplayName("Test for employee update")
    @Test
    public void givenEmpoyeeObject_whenUpdate_thenReturnUpdatedEmployeeObject() {
        // given
        //Employee employee = testEmployeeData();
        employeeRepsoitory.save(employee);
        //when
        Optional<Employee> savedEmployee = employeeRepsoitory.findByEmail("ramesh@gmil.com");
        Assertions.assertThat(savedEmployee.isPresent()).isFalse();
        savedEmployee = employeeRepsoitory.findByEmail("ramesh@gmail.com");
        Assertions.assertThat(savedEmployee.isPresent()).isTrue();
        savedEmployee.get().setEmail("ramesh2@yahoo.com");
        savedEmployee.get().setLastName("Guru");
        employeeRepsoitory.save(savedEmployee.get());
        //then
        Assertions.assertThat(savedEmployee.isPresent()).isTrue();
        Assertions.assertThat(savedEmployee.get().getEmail())
                .isNotNull()
                .isEqualTo("ramesh2@yahoo.com");
        Assertions.assertThat(savedEmployee.get().getLastName())
                .isNotNull()
                .isEqualTo("Guru");
    }

    @DisplayName("JUnit Test employee delete operation")
    @Test
    public void givenEmployeeObject_whenDelete_thenDoNotReturnEmployeeeObject() {
        // given
        //Employee employee = employeeRepsoitory.save(testEmployeeData());
        //when
        employeeRepsoitory.deleteById(employee.getId());
        //then
        Optional<Employee> deletedEmployee = employeeRepsoitory.findById(employee.getId());
        Assertions.assertThat(deletedEmployee.isEmpty()).isTrue();
    }
    @DisplayName("Junit test to find employee by JPQL using index parameter")
    @Test
    public void givenEmployeeeObect_whenFindByFirstNameAndLastName_thenReturnMatchingEmployeeObject() {
        // given
        //Employee employee = testEmployeeData();
        employeeRepsoitory.save(employee);
        //when
        Optional<Employee> searchedEmployee = employeeRepsoitory.findEmployeeByJPQL("Ramesh","Fadatre" );
        //then
        Assertions.assertThat(searchedEmployee).isPresent();
        Assertions.assertThat(searchedEmployee.get()).isNotNull();

        searchedEmployee = employeeRepsoitory.findEmployeeByJPQL("Ramesh2","Fadatre" );
        //then
        Assertions.assertThat(searchedEmployee).isEmpty();
    }

    @DisplayName("Junit test to find employee by JPQL using named parameter")
    @Test
    public void givenEmployeeeObect_whenFindByFirstNameAndLastNameUsingNamedParam_thenReturnMatchingEmployeeObject() {
        // given
        //Employee employee = testEmployeeData();
        employeeRepsoitory.save(employee);
        //when
        Optional<Employee> searchedEmployee = employeeRepsoitory.findEmployeeByJPQL("Ramesh","Fadatre" );
        //then
        Assertions.assertThat(searchedEmployee).isPresent();
        Assertions.assertThat(searchedEmployee.get()).isNotNull();

        searchedEmployee = employeeRepsoitory.findEmployeeByJPQLNamedparam("Ramesh2","Fadatre" );
        //then
        Assertions.assertThat(searchedEmployee).isEmpty();
    }
    @DisplayName("Junit test to find employee by JPQL using Native Query")
    @Test
    public void givenEmployeeeObect_whenFindByFirstNameAndLastNameUsingNNativeQuery_thenReturnMatchingEmployeeObject() {
        // given
        //Employee employee = testEmployeeData();
        employeeRepsoitory.save(employee);
        //when
        Employee searchedEmployee = employeeRepsoitory.findEmployeeByFirstNameAndLastNameWithNativeQuery("Ramesh","Fadatre" );
        //then
        Assertions.assertThat(searchedEmployee).isNotNull();

        searchedEmployee = employeeRepsoitory.findEmployeeByFirstNameAndLastNameWithNativeQuery ("Ramesh2","Fadatre" );
        //then
        Assertions.assertThat(searchedEmployee).isNull();
    }

    @DisplayName("Junit test to find employee by JPQL using Native Query using named parameters")
    @Test
    public void givenEmployeeeObect_whenFindByFirstNameAndLastNameUsingNativeQueryNamed_thenReturnMatchingEmployeeObject() {
        // given
        //Employee employee = testEmployeeData();
        employeeRepsoitory.save(employee);
        //when
        Employee searchedEmployee = employeeRepsoitory.findEmployeeByFirstNameAndLastNameWithNativeQueryNamedParam("Ramesh","Fadatre" );
        //then
        Assertions.assertThat(searchedEmployee).isNotNull();


        searchedEmployee = employeeRepsoitory.findEmployeeByFirstNameAndLastNameWithNativeQueryNamedParam("Ramesh2","Fadatre" );
        //then
        Assertions.assertThat(searchedEmployee).isNull();
    }
}



