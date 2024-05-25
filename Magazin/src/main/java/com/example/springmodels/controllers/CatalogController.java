package com.example.springmodels.controllers;

import com.example.springmodels.Service.ProductService;
import com.example.springmodels.Service.ShoppingCartService;
import com.example.springmodels.models.Client;
import com.example.springmodels.models.Product;
import com.example.springmodels.models.Review;
import com.example.springmodels.repos.ProductRepository;
import com.example.springmodels.repos.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Controller
public class CatalogController {

    private final ProductService productService;
    private final ShoppingCartService shoppingCartService;

    private ReviewRepository reviewRepository;
    private final ProductRepository productRepository;


    @Autowired
    public CatalogController(ProductService productService, ShoppingCartService shoppingCartService, ReviewRepository reviewRepository, ProductRepository productRepository) {
        this.productService = productService;
        this.shoppingCartService = shoppingCartService;
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
    }

    @GetMapping("/catalog")
    public String showCatalogPage(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "6") int size,
            Model model
    ) {
        Page<Product> productPage = productService.getProductsPaginated(page, size);
        List<Product> products = productPage.getContent();

        model.addAttribute("products", products);
        model.addAttribute("productPage", productPage);

        return "catalog";
    }

    @GetMapping("/catalog/search")
    public String searchProducts(@RequestParam String keyword, Model model) {
        List<Product> searchResults = productService.searchProducts(keyword);
        model.addAttribute("products", searchResults);
        return "fragments/productList :: productList";
    }

    @GetMapping("/catalog/filter")
    public String filterProducts(@RequestParam int categoryId, Model model) {
        List<Product> filteredProducts = productService.filterProductsByCategory(categoryId);
        model.addAttribute("products", filteredProducts);
        return "fragments/productList :: productList";
    }

    @GetMapping("/catalog/sort")
    public String sortProducts(@RequestParam int sortOption, Model model) {
        List<Product> sortedProducts;

        switch (sortOption) {
            case 1:
                sortedProducts = productService.sortProductsByPriceAsc();
                break;
            case 2:
                sortedProducts = productService.sortProductsByPriceDesc();
                break;
            case 3:
                sortedProducts = productService.sortProductsByName();
                break;
            default:
                sortedProducts = productService.getAllProducts();
        }

        model.addAttribute("products", sortedProducts);
        return "fragments/productList :: productList";
    }

    @PostMapping("/catalog/add-to-cart")
    public String addToCart(@RequestParam Long productId, @AuthenticationPrincipal Client client) {
        Product product = productService.getProductById(productId);

        return "redirect:/catalog/product/" + productId;
    }


    @PostMapping("/catalog/add-review")
    public String addReview(@RequestParam Long productId, @ModelAttribute Review review) {
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            review.setProduct(product);
            reviewRepository.save(review);

            return "redirect:/catalog/product/" + productId;
        } else {
            return "redirect:/error";
        }
    }

    @GetMapping("/product/{productId}")
    public String getProductPage(@PathVariable Long productId, Model model) {

        List<Review> reviews = reviewRepository.findAll();
        model.addAttribute("reviews", reviews);

        return "product-page";
    }

    @GetMapping("/catalog/product/{productId}")
    public String showProductDetails(@PathVariable Long productId, Model model) {
        Product product = productService.getProductById(productId);
        model.addAttribute("product", product);

        List<Review> reviews = reviewRepository.findAll();
        model.addAttribute("reviews", reviews);

        Review review = new Review();
        model.addAttribute("review", review);

        return "product-page";
    }

}
