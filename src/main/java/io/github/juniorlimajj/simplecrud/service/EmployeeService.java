package io.github.juniorlimajj.simplecrud.service;

import io.github.juniorlimajj.simplecrud.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {
  ResponseEntity<List<Employee>> getEmployees();
  ResponseEntity<Employee> getEmployeeById(Long id);
  ResponseEntity<Employee> createEmployee(Employee employee);
  ResponseEntity<Employee> updateEmployee(Long id, Employee employee);
  ResponseEntity<HttpStatus> deleteEmployeeById(Long id);
  ResponseEntity<HttpStatus> deleteAllEmployees();
  Employee getEmployee(Long id);
}
