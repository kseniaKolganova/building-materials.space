package com.example.springmodels.controllers;

import com.example.springmodels.models.Review;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ReviewsController {
    private List<Review> reviews = new ArrayList<>();

    @GetMapping("/reviews")
    public String showReviews(Model model) {
        model.addAttribute("reviews", reviews);
        return "reviews :: reviewsList";
    }
}

