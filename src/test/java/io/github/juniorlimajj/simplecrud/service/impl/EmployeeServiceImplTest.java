package io.github.juniorlimajj.simplecrud.service.impl;

import io.github.juniorlimajj.simplecrud.model.Employee;
import io.github.juniorlimajj.simplecrud.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

  @Mock
  private EmployeeRepository mockEmployeeRepository;

  private EmployeeServiceImpl employeeServiceImplUnderTest;

  @BeforeEach
  void setUp() {
    employeeServiceImplUnderTest = new EmployeeServiceImpl(mockEmployeeRepository);
  }

  @Test
  void testGetEmployees() {
    final ResponseEntity<List<Employee>> expectedResult = new ResponseEntity<>(
        List.of(new Employee(0L, "name", "role")), HttpStatus.OK);

    final List<Employee> employees = List.of(new Employee(0L, "name", "role"));
    when(mockEmployeeRepository.findAll()).thenReturn(employees);

    final ResponseEntity<List<Employee>> result = employeeServiceImplUnderTest.getEmployees();

    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void testGetEmployees_EmployeeRepositoryReturnsNoItems() {
    when(mockEmployeeRepository.findAll()).thenReturn(Collections.emptyList());

    final ResponseEntity<List<Employee>> result = employeeServiceImplUnderTest.getEmployees();

    assertThat(result).isEqualTo(ResponseEntity.ok(Collections.emptyList()));
  }

  @Test
  void testGetEmployeeById() {
    final ResponseEntity<Employee> expectedResult = new ResponseEntity<>(new Employee(0L, "name", "role"),
        HttpStatus.OK);

    final Optional<Employee> employee = Optional.of(new Employee(0L, "name", "role"));
    when(mockEmployeeRepository.findById(0L)).thenReturn(employee);

    final ResponseEntity<Employee> result = employeeServiceImplUnderTest.getEmployeeById(0L);

    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void testGetEmployeeById_EmployeeRepositoryReturnsAbsent() {
    final ResponseEntity<Employee> expectedResult = new ResponseEntity<>(new Employee(0L, "name", "role"),
        HttpStatus.OK);
    when(mockEmployeeRepository.findById(0L)).thenReturn(Optional.of(new Employee().builder()
        .name("name")
        .role("role")
        .build()));

    final ResponseEntity<Employee> result = employeeServiceImplUnderTest.getEmployeeById(0L);

    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void testCreateEmployee() {
    final Employee employee = new Employee(0L, "name", "role");
    final ResponseEntity<Employee> expectedResult = new ResponseEntity<>(new Employee(0L, "name", "role"),
        HttpStatus.CREATED);
    when(mockEmployeeRepository.save(new Employee(0L, "name", "role")))
        .thenReturn(new Employee(0L, "name", "role"));

    final ResponseEntity<Employee> result = employeeServiceImplUnderTest.createEmployee(employee);

    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void testUpdateEmployee() {
    final Employee employee = new Employee(0L, "name", "role");
    final ResponseEntity<Employee> expectedResult = new ResponseEntity<>(new Employee(0L, "name", "role"),
        HttpStatus.OK);

    final Optional<Employee> employee1 = Optional.of(new Employee(0L, "name", "role"));
    when(mockEmployeeRepository.findById(0L)).thenReturn(employee1);

    when(mockEmployeeRepository.save(new Employee(0L, "name", "role")))
        .thenReturn(new Employee(0L, "name", "role"));

    final ResponseEntity<Employee> result = employeeServiceImplUnderTest.updateEmployee(0L, employee);

    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void testUpdateEmployee_EmployeeRepositoryFindByIdReturnsAbsent() {
    final Employee employee = new Employee(0L, "name", "role");
    final ResponseEntity<Employee> expectedResult = new ResponseEntity<>(HttpStatus.OK);
    when(mockEmployeeRepository.findById(0L)).thenReturn(Optional.of(employee));

    final ResponseEntity<Employee> result = employeeServiceImplUnderTest.updateEmployee(0L, employee);

    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void testDeleteEmployeeById() {
    final ResponseEntity<HttpStatus> expectedResult = new ResponseEntity<>(HttpStatus.OK);

    final Optional<Employee> employee = Optional.of(new Employee(0L, "name", "role"));
    when(mockEmployeeRepository.findById(0L)).thenReturn(employee);

    final ResponseEntity<HttpStatus> result = employeeServiceImplUnderTest.deleteEmployeeById(0L);

    assertThat(result).isEqualTo(expectedResult);
    verify(mockEmployeeRepository).delete(new Employee(0L, "name", "role"));
  }

  @Test
  void testDeleteEmployeeById_EmployeeRepositoryFindByIdReturnsAbsent() {
    final ResponseEntity<HttpStatus> expectedResult = new ResponseEntity<>(HttpStatus.OK);
    when(mockEmployeeRepository.findById(0L)).thenReturn(Optional.of(new Employee()));

    final ResponseEntity<HttpStatus> result = employeeServiceImplUnderTest.deleteEmployeeById(0L);

    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void testDeleteAllEmployees() {
    final ResponseEntity<HttpStatus> expectedResult = new ResponseEntity<>(HttpStatus.OK);

    final ResponseEntity<HttpStatus> result = employeeServiceImplUnderTest.deleteAllEmployees();

    assertThat(result).isEqualTo(expectedResult);
    verify(mockEmployeeRepository).deleteAll();
  }

  @Test
  void testGetEmployee() {
    final Employee expectedResult = new Employee(0L, "name", "role");

    final Optional<Employee> employee = Optional.of(new Employee(0L, "name", "role"));
    when(mockEmployeeRepository.findById(0L)).thenReturn(employee);

    final Employee result = employeeServiceImplUnderTest.getEmployee(0L);

    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void testGetEmployee_EmployeeRepositoryReturnsAbsent() {
    when(mockEmployeeRepository.findById(0L)).thenReturn(Optional.empty());

    final Employee result = employeeServiceImplUnderTest.getEmployee(0L);

    assertThat(result).isNull();
  }
}
