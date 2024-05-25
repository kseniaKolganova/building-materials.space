package com.example.administratorpanel.controller;

import com.example.administratorpanel.model.Suppler;
import com.example.administratorpanel.repo.SupplerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/suppliers")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class SupplerController {

    private final SupplerRepository supplerRepo;

    @Autowired
    public SupplerController(SupplerRepository supplerRepo) {
        this.supplerRepo = supplerRepo;
    }

    @GetMapping()
    public String listSuppliers(Model model) {
        Iterable<Suppler> suppliers = supplerRepo.findAll();
        model.addAttribute("suppliers", suppliers);
        return "suppliers/allSuppliers";
    }

    @GetMapping("/addSupplier")
    public String showAddSupplierForm(Model model) {
        Suppler supplier = new Suppler();
        model.addAttribute("supplier", supplier);
        return "suppliers/addSupplier";
    }

    @PostMapping("/addSupplier")
    public String addSupplier(@Valid @ModelAttribute("supplier") Suppler supplier, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "suppliers/addSupplier";
        }
        supplerRepo.save(supplier);
        return "redirect:/suppliers";
    }

    @GetMapping("/editSupplier/{id}")
    public String showEditSupplierForm(@PathVariable("id") Long id, Model model) {
        Suppler supplier = supplerRepo.findById(id).orElse(null);
        if (supplier == null) {
            return "redirect:/suppliers";
        }
        model.addAttribute("supplier", supplier);
        return "suppliers/editSupplier";
    }

    @PostMapping("/editSupplier/{id}")
    public String editSupplier(@PathVariable("id") Long id, @Valid @ModelAttribute("supplier") Suppler supplier, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "suppliers/editSupplier";
        }
        supplier.setId(id);
        supplerRepo.save(supplier);
        return "redirect:/suppliers";
    }

    @GetMapping("/deleteSupplier/{id}")
    public String deleteSupplier(@PathVariable("id") Long id) {
        try {
            supplerRepo.deleteById(id);
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
        }
        return "redirect:/suppliers";
    }
}
