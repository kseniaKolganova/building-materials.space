package com.example.administratorpanel.repo;

import com.example.administratorpanel.model.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
    Post findByName(String name);
}
