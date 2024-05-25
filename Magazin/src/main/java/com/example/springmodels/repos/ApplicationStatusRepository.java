package com.example.springmodels.repos;

import com.example.springmodels.models.Application;
import com.example.springmodels.models.ApplicationStatus;
import org.springframework.data.repository.CrudRepository;

public interface ApplicationStatusRepository extends CrudRepository<ApplicationStatus, Long> {
    ApplicationStatus findByName(String name);
}
