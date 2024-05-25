package com.example.springmodels.repos;

import com.example.springmodels.models.Application;
import org.springframework.data.repository.CrudRepository;

    public interface ApplicationRepository extends CrudRepository<Application, Long> {
        Application findByNumber(String number);
    }

