package com.example.springmodels.repos;

import com.example.springmodels.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // You can add custom query methods if needed
}
