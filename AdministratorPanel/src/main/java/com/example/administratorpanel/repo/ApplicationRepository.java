package com.example.administratorpanel.repo;

import com.example.administratorpanel.model.Application;
import org.springframework.data.repository.CrudRepository;

    public interface ApplicationRepository extends CrudRepository<Application, Long> {
        Application findByNumber(String number);
    }

