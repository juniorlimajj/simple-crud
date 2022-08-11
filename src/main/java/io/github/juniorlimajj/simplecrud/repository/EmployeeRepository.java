package io.github.juniorlimajj.simplecrud.repository;

import io.github.juniorlimajj.simplecrud.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
