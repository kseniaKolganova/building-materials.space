package com.example.administratorpanel.repo;

import com.example.administratorpanel.model.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    Employee findByLogin(String login);
}
