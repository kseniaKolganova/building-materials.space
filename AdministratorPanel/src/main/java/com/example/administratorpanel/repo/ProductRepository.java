package com.example.administratorpanel.repo;

import com.example.administratorpanel.model.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
    Product findByArticul(String articul);

    List<Product> findByNameContainingIgnoreCase(String keyword);

    List<Product> findByCategoryId(int categoryId);

    List<Product> findAllByOrderByPriceAsc();

    List<Product> findAllByOrderByPriceDesc();

    List<Product> findAllByOrderByName();

    Optional<Product> findById(Long id);

    List<Product> findProductById(Long id);
}