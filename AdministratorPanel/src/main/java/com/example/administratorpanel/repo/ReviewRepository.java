package com.example.administratorpanel.repo;

import com.example.administratorpanel.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
