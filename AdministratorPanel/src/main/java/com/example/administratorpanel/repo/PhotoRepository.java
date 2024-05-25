package com.example.administratorpanel.repo;

import com.example.administratorpanel.model.Photo;
import org.springframework.data.repository.CrudRepository;

public interface PhotoRepository extends CrudRepository<Photo, Long> {
    Photo findByUrl(String url);
}
