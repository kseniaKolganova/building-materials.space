package com.example.springmodels.repos;

import com.example.springmodels.models.CompoundApplication;
import org.springframework.data.repository.CrudRepository;

public interface CompoundApplicationRepository extends CrudRepository<CompoundApplication, Long> {
    CompoundApplication findByQuantityItems(String quantityItems);
}
