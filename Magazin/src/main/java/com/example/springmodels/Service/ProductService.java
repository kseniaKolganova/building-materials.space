package com.example.springmodels.Service;

import com.example.springmodels.models.Product;
import com.example.springmodels.repos.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {

    private final ProductRepository productRepository;


    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return (List<Product>) productRepository.findAll();
    }

    public List<Product> searchProducts(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword);
    }

    public List<Product> filterProductsByCategory(int categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public List<Product> sortProductsByPriceAsc() {
        return productRepository.findAllByOrderByPriceAsc();
    }

    public List<Product> sortProductsByPriceDesc() {
        return productRepository.findAllByOrderByPriceDesc();
    }


    public List<Product> sortProducts(int sortOption) {
        switch (sortOption) {
            case 1:
                return productRepository.findAllByOrderByPriceAsc();
            case 2:
                return productRepository.findAllByOrderByPriceDesc();
            default:
                return getAllProducts();
        }
    }
    public List<Product> sortProductsByName() {
        return productRepository.findAllByOrderByName();
    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found with id: " + productId));
    }


    public Page<Product> getProductsPaginated(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }

}

