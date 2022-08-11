package io.github.juniorlimajj.simplecrud.service.impl;

import io.github.juniorlimajj.simplecrud.model.Employee;
import io.github.juniorlimajj.simplecrud.repository.EmployeeRepository;
import io.github.juniorlimajj.simplecrud.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class EmployeeServiceImpl implements EmployeeService {

  private EmployeeRepository employeeRepository;

  @Autowired
  public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  @Override
  public ResponseEntity<List<Employee>> getEmployees() {
    try {
      return new ResponseEntity<>(employeeRepository.findAll(),HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public ResponseEntity<Employee> getEmployeeById(Long id) {
    try {
      Employee employeeResultSet = getEmployee(id);
      if (employeeResultSet != null){
        return new ResponseEntity<>(employeeResultSet, HttpStatus.OK);
      }
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public ResponseEntity<Employee> createEmployee(Employee employee) {
    Employee newEmployee = employeeRepository
        .save(Employee.builder()
            .name(employee.getName())
            .role(employee.getRole())
            .build());
    return new ResponseEntity<>(newEmployee, HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<Employee> updateEmployee(Long id, Employee employee) {
    Employee employeeResultSet = getEmployee(id);
    if(employeeResultSet != null){
      employeeResultSet.setName(employee.getName());
      employeeResultSet.setRole(employee.getRole());
      return new ResponseEntity<>(employeeRepository.save(employeeResultSet),HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @Override
  public ResponseEntity<HttpStatus> deleteEmployeeById(Long id) {
    try {
      Employee employeeResultSet = getEmployee(id);
      if(employeeResultSet != null){
        employeeRepository.delete(employeeResultSet);
        return new ResponseEntity<>(HttpStatus.OK);
      }
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public ResponseEntity<HttpStatus> deleteAllEmployees() {
    try {
      employeeRepository.deleteAll();
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public Employee getEmployee(Long id) {
    Optional<Employee> empObj = employeeRepository.findById(id);
    if (empObj.isPresent()) {
      return empObj.get();
    }
    return null;
  }
}
