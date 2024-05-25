package com.example.springmodels.repos;

import com.example.springmodels.models.Photo;
import org.springframework.data.repository.CrudRepository;

public interface PhotoRepository extends CrudRepository<Photo, Long> {
    Photo findByUrl(String url);
}
