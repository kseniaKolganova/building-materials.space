package com.example.springmodels.repos;

import com.example.springmodels.models.Logs;
import org.springframework.data.repository.CrudRepository;

public interface LogsRepository extends CrudRepository<Logs, Long> {
}
