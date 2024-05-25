package com.example.springmodels.repos;

import com.example.springmodels.models.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    Employee findBySurname(String surname);
}
