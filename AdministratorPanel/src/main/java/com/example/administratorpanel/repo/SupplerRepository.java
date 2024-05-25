package com.example.administratorpanel.repo;

import com.example.administratorpanel.model.Suppler;
import org.springframework.data.repository.CrudRepository;

public interface SupplerRepository extends CrudRepository<Suppler, Long> {
    Suppler findByAddress(String address);
}
