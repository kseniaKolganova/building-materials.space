package com.example.administratorpanel.controller;

import com.example.administratorpanel.model.ApplicationStatus;
import com.example.administratorpanel.repo.ApplicationStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/application-status")
public class ApplicationStatusController {

    private final ApplicationStatusRepository applicationStatusRepo;

    @Autowired
    public ApplicationStatusController(ApplicationStatusRepository applicationStatusRepo) {
        this.applicationStatusRepo = applicationStatusRepo;
    }

    @GetMapping()
    public String listApplicationStatus(Model model) {
        Iterable<ApplicationStatus> applicationStatusList = applicationStatusRepo.findAll();
        model.addAttribute("applicationStatusList", applicationStatusList);
        return "application-status/allApplicationStatus";
    }

    @GetMapping("/add")
    public String showAddApplicationStatusForm(Model model) {
        ApplicationStatus applicationStatus = new ApplicationStatus();
        model.addAttribute("applicationStatus", applicationStatus);
        return "application-status/addApplicationStatus";
    }

    @PostMapping("/add")
    public String addApplicationStatus(@Valid @ModelAttribute("applicationStatus") ApplicationStatus applicationStatus,
                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "application-status/addApplicationStatus";
        }
        applicationStatusRepo.save(applicationStatus);
        return "redirect:/application-status";
    }

    @GetMapping("/edit/{id}")
    public String showEditApplicationStatusForm(@PathVariable("id") Long id, Model model) {
        ApplicationStatus applicationStatus = applicationStatusRepo.findById(id).orElse(null);
        if (applicationStatus == null) {
            return "redirect:/application-status";
        }
        model.addAttribute("applicationStatus", applicationStatus);
        return "application-status/editApplicationStatus";
    }

    @PostMapping("/edit/{id}")
    public String editApplicationStatus(@PathVariable("id") Long id,
                                        @Valid @ModelAttribute("applicationStatus") ApplicationStatus applicationStatus,
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "application-status/editApplicationStatus";
        }
        applicationStatus.setId(id);
        applicationStatusRepo.save(applicationStatus);
        return "redirect:/application-status";
    }

    @GetMapping("/delete/{id}")
    public String deleteApplicationStatus(@PathVariable("id") Long id) {
        try {
            System.out.println("Deleting application status with ID: " + id);
            applicationStatusRepo.deleteById(id);
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
        }
        return "redirect:/application-status";
    }
}
