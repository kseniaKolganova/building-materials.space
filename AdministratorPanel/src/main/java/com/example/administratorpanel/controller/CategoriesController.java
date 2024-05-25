package com.example.administratorpanel.controller;

import com.example.administratorpanel.model.Categories;
import com.example.administratorpanel.repo.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/categories")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class CategoriesController {

    private final CategoriesRepository categoriesRepo;

    @Autowired
    public CategoriesController(CategoriesRepository categoriesRepo) {
        this.categoriesRepo = categoriesRepo;
    }

    @GetMapping()
    public String listCategories(Model model) {
        Iterable<Categories> categories = categoriesRepo.findAll();
        model.addAttribute("categories", categories);
        return "categories/allCategories";
    }

    @GetMapping("/addCategory")
    public String showAddCategoryForm(Model model) {
        Categories category = new Categories();
        model.addAttribute("category", category);
        return "categories/addCategory";
    }

    @PostMapping("/addCategory")
    public String addCategory(@Valid @ModelAttribute("category") Categories category, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "categories/addCategory";
        }
        System.out.println("Name: " + category.getName());
        categoriesRepo.save(category);
        return "redirect:/categories";
    }

    @GetMapping("/editCategory/{id}")
    public String showEditCategoryForm(@PathVariable("id") int id, Model model) {
        Categories category = categoriesRepo.findById((long) id).orElse(null);
        if (category == null) {
            return "redirect:/categories";
        }
        model.addAttribute("category", category);
        return "categories/editCategory";
    }

    @PostMapping("/editCategory/{id}")
    public String editCategory(@PathVariable("id") int id, @Valid @ModelAttribute("category") Categories category, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "categories/editCategory";
        }
        category.setId((long) id);
        categoriesRepo.save(category);
        return "redirect:/categories";
    }

    @GetMapping("/deleteCategory/{id}")
    public String deleteCategory(@PathVariable("id") int id) {
        try {
            System.out.println("Deleting category with ID: " + id);
            categoriesRepo.deleteById((long) id);
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
        }
        return "redirect:/categories";
    }
}
