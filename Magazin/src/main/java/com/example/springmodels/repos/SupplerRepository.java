package com.example.springmodels.repos;

import com.example.springmodels.models.Suppler;
import org.springframework.data.repository.CrudRepository;

public interface SupplerRepository extends CrudRepository<Suppler, Long> {
    Suppler findByAddress(String address);
}
