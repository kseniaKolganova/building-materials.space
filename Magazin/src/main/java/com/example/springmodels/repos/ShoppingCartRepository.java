package com.example.springmodels.repos;

import com.example.springmodels.models.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    List<ShoppingCart> findByUserId(Long userId);
    ShoppingCart findByProductIdAndUserId(Long productId, Long userId);
    List<ShoppingCart> findByOrderId(Long orderId);

}