package com.example.springmodels.repos;

import com.example.springmodels.models.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
    Post findByName(String name);
}
