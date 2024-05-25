package com.example.administratorpanel.controller;

import com.example.administratorpanel.model.Application;
import com.example.administratorpanel.model.ApplicationStatus;
import com.example.administratorpanel.model.Suppler;
import com.example.administratorpanel.repo.ApplicationRepository;
import com.example.administratorpanel.repo.ApplicationStatusRepository;
import com.example.administratorpanel.repo.SupplerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.util.List;

@Controller
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationRepository applicationRepo;
    private final ApplicationStatusRepository applicationStatusRepo;
    private final SupplerRepository supplerRepo;

    @Autowired
    public ApplicationController(ApplicationRepository applicationRepo, ApplicationStatusRepository applicationStatusRepo, SupplerRepository supplerRepo) {
        this.applicationRepo = applicationRepo;
        this.applicationStatusRepo = applicationStatusRepo;
        this.supplerRepo = supplerRepo;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(ApplicationStatus.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                ApplicationStatus status = applicationStatusRepo.findById(Long.parseLong(text)).orElse(null);
                setValue(status);
            }
        });

        binder.registerCustomEditor(Suppler.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                Suppler suppler = supplerRepo.findById(Long.parseLong(text)).orElse(null);
                setValue(suppler);
            }
        });

        binder.registerCustomEditor(Timestamp.class, new TimestampEditor());
    }

    @GetMapping()
    public String listApplications(Model model) {
        List<Application> applications = (List<Application>) applicationRepo.findAll();
        model.addAttribute("applicate", applications);
        return "applications/allApplications";
    }

    @GetMapping("/addApplication")
    public String showAddApplicationForm(Model model) {
        Application application = new Application();
        List<ApplicationStatus> applicationStatuses = (List<ApplicationStatus>) applicationStatusRepo.findAll();
        List<Suppler> supplers = (List<Suppler>) supplerRepo.findAll();
        model.addAttribute("application", application);
        model.addAttribute("applicationStatuses", applicationStatuses);
        model.addAttribute("supplers", supplers);
        return "applications/addApplication";
    }

    @PostMapping("/addApplication")
    public String addApplication(@Valid @ModelAttribute("application") Application application, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "applications/addApplication";
        }
        applicationRepo.save(application);
        return "redirect:/applications";
    }

    @GetMapping("/editApplication/{id}")
    public String showEditApplicationForm(@PathVariable("id") Long id, Model model) {
        Application applicate = applicationRepo.findById(id).orElse(null);
        List<ApplicationStatus> applicationStatuses = (List<ApplicationStatus>) applicationStatusRepo.findAll();
        List<Suppler> supplers = (List<Suppler>) supplerRepo.findAll();
        if (applicate == null) {
            return "redirect:/applications";
        }
        model.addAttribute("applicate", applicate);  // Убедитесь, что здесь устанавливается id
        model.addAttribute("applicationStatuses", applicationStatuses);
        model.addAttribute("supplers", supplers);
        return "applications/editApplication";
    }

    @PostMapping("/editApplication/{id}")
    public String editApplication(@PathVariable("id") Long id, @Valid @ModelAttribute("application") Application applicate, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "applications/editApplication";
        }
        applicate.setId(id);
        applicationRepo.save(applicate);
        return "redirect:/applications";
    }

    @GetMapping("/deleteApplication/{id}")
    public String deleteApplication(@PathVariable("id") Long id) {
        try {
            applicationRepo.deleteById(id);
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
        }
        return "redirect:/applications";
    }
}
