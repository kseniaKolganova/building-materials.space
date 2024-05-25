package com.example.administratorpanel.controller;

import com.example.administratorpanel.model.CompoundApplication;
import com.example.administratorpanel.model.Product;
import com.example.administratorpanel.model.Application;
import com.example.administratorpanel.repo.CompoundApplicationRepository;
import com.example.administratorpanel.repo.ProductRepository;
import com.example.administratorpanel.repo.ApplicationRepository;
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
@RequestMapping("/compoundApplications")
public class CompoundApplicationController {

    private final CompoundApplicationRepository compoundApplicationRepo;
    private final ProductRepository productRepo;
    private final ApplicationRepository applicationRepo;

    @Autowired
    public CompoundApplicationController(CompoundApplicationRepository compoundApplicationRepo,
                                         ProductRepository productRepo,
                                         ApplicationRepository applicationRepo) {
        this.compoundApplicationRepo = compoundApplicationRepo;
        this.productRepo = productRepo;
        this.applicationRepo = applicationRepo;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Product.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                Product product = productRepo.findById(Long.parseLong(text)).orElse(null);
                setValue(product);
            }
        });

        binder.registerCustomEditor(Application.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                Application app = applicationRepo.findById(Long.parseLong(text)).orElse(null);
                setValue(app);
            }
        });
    }

    @GetMapping()
    public String listCompoundApplications(Model model) {
        List<CompoundApplication> compoundApplications = (List<CompoundApplication>) compoundApplicationRepo.findAll();
        model.addAttribute("compoundApplications", compoundApplications);
        return "compoundApplications/allCompoundApplications";
    }

    @GetMapping("/addCompoundApplication")
    public String showAddCompoundApplicationForm(Model model) {
        CompoundApplication compoundApplication = new CompoundApplication();
        List<Product> products = (List<Product>) productRepo.findAll();
        List<Application> applications = (List<Application>) applicationRepo.findAll();
        model.addAttribute("compoundApplication", compoundApplication);
        model.addAttribute("products", products);
        model.addAttribute("applications", applications);
        return "compoundApplications/addCompoundApplication";
    }

    @PostMapping("/addCompoundApplication")
    public String addCompoundApplication(@Valid @ModelAttribute("compoundApplication") CompoundApplication compoundApplication,
                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "compoundApplications/addCompoundApplication";
        }
        compoundApplicationRepo.save(compoundApplication);
        return "redirect:/compoundApplications";
    }

    @GetMapping("/editCompoundApplication/{id}")
    public String showEditCompoundApplicationForm(@PathVariable("id") int id, Model model) {
        CompoundApplication compoundApplication = compoundApplicationRepo.findById((long) id).orElse(null);
        List<Product> products = (List<Product>) productRepo.findAll();
        List<Application> applications = (List<Application>) applicationRepo.findAll();
        if (compoundApplication == null) {
            return "redirect:/compoundApplications";
        }
        model.addAttribute("compoundApplication", compoundApplication);
        model.addAttribute("products", products);
        model.addAttribute("applications", applications);
        return "compoundApplications/editCompoundApplication";
    }

    @PostMapping("/editCompoundApplication/{id}")
    public String editCompoundApplication(@PathVariable("id") int id,
                                          @Valid @ModelAttribute("compoundApplication") CompoundApplication compoundApplication,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "compoundApplications/editCompoundApplication";
        }
        compoundApplicationRepo.save(compoundApplication);
        return "redirect:/compoundApplications";
    }

    @GetMapping("/deleteCompoundApplication/{id}")
    public String deleteCompoundApplication(@PathVariable("id") int id) {
        try {
            compoundApplicationRepo.deleteById((long) id);
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
        }
        return "redirect:/compoundApplications";
    }
}
