package io.github.juniorlimajj.simplecrud.controller;

import io.github.juniorlimajj.simplecrud.model.Employee;
import io.github.juniorlimajj.simplecrud.service.impl.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {
  private EmployeeServiceImpl employeeServiceImpl;

  @Autowired
  public EmployeeController(EmployeeServiceImpl employeeServiceImpl) {
    this.employeeServiceImpl = employeeServiceImpl;
  }

  @GetMapping("/get/employees")
  public ResponseEntity<List<Employee>> getEmployees() {
    return employeeServiceImpl.getEmployees();
  }

  @GetMapping("/get/employee/{id}")
  public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") long id) {
    return employeeServiceImpl.getEmployeeById(id);
  }

  @PostMapping("/create/employee")
  public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
    return employeeServiceImpl.createEmployee(employee);
  }

  @PutMapping("/put/employee/{id}")
  public ResponseEntity<Employee> updateEmployee(@PathVariable("id") long id, @RequestBody Employee employee) {
    return employeeServiceImpl.updateEmployee(id, employee);
  }

  @DeleteMapping("/delete/employee/{id}")
  public ResponseEntity<HttpStatus> deleteEmployeeById(@PathVariable("id") long id) {
    return employeeServiceImpl.deleteEmployeeById(id);
  }

  @DeleteMapping("/delete/employees")
  public ResponseEntity<HttpStatus> deleteAllEmployees() {
    return employeeServiceImpl.deleteAllEmployees();
  }
}
