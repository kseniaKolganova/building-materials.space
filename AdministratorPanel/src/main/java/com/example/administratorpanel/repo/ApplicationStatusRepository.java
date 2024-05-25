package com.example.administratorpanel.repo;

import com.example.administratorpanel.model.ApplicationStatus;
import org.springframework.data.repository.CrudRepository;

public interface ApplicationStatusRepository extends CrudRepository<ApplicationStatus, Long> {
    ApplicationStatus findByName(String name);
}
