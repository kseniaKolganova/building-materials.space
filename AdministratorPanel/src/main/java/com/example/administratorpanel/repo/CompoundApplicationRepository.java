package com.example.administratorpanel.repo;

import com.example.administratorpanel.model.CompoundApplication;
import org.springframework.data.repository.CrudRepository;

public interface CompoundApplicationRepository extends CrudRepository<CompoundApplication, Long> {
    CompoundApplication findByQuantityItems(String quantityItems);
}
