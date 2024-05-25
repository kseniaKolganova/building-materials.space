package com.example.administratorpanel.controller;

import com.example.administratorpanel.model.Product;
import com.example.administratorpanel.model.Review;
import com.example.administratorpanel.repo.ProductRepository;
import com.example.administratorpanel.repo.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.util.List;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ReviewController(ReviewRepository reviewRepository, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Product.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                Product product = productRepository.findById(Long.parseLong(text)).orElse(null);
                setValue(product);
            }
        });
    }

    @GetMapping()
    public String listReviews(Model model) {
        List<Review> reviews = (List<Review>) reviewRepository.findAll();
        model.addAttribute("reviews", reviews);
        return "reviews/allReviews";
    }

    @GetMapping("/addReview")
    public String showAddReviewForm(Model model) {
        Review review = new Review();
        List<Product> products = (List<Product>) productRepository.findAll();
        model.addAttribute("review", review);
        model.addAttribute("products", products);
        return "reviews/addReview";
    }

    @PostMapping("/addReview")
    public String addReview(@Valid @ModelAttribute Review review, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "reviews/addReview";
        }
        reviewRepository.save(review);
        return "redirect:/reviews";
    }

    @GetMapping("/editReview/{id}")
    public String showEditReviewForm(@PathVariable Long id, Model model) {
        Review review = reviewRepository.findById(id).orElse(null);
        List<Product> products = (List<Product>) productRepository.findAll();
        if (review == null) {
            return "redirect:/reviews";
        }
        model.addAttribute("review", review);
        model.addAttribute("products", products);
        return "reviews/editReview";
    }

    @PostMapping("/editReview/{id}")
    public String editReview(@PathVariable Long id, @Valid @ModelAttribute Review review, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "reviews/editReview";
        }
        reviewRepository.save(review);
        return "redirect:/reviews";
    }

    @GetMapping("/deleteReview/{id}")
    public String deleteReview(@PathVariable Long id) {
        try {
            reviewRepository.deleteById(id);
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
        }
        return "redirect:/reviews";
    }

}
