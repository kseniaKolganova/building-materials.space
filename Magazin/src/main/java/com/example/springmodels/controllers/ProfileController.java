package com.example.springmodels.controllers;

import com.example.springmodels.models.Client;
import com.example.springmodels.models.Orders;
import com.example.springmodels.repos.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProfileController {

    private final ClientRepository clientRepository;

    @Autowired
    public ProfileController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping("/profile")
    public String showProfile(@AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails,
                              Model model) {
        String username = userDetails.getUsername();
        Client client = clientRepository.findByLogin(username);

        if (client != null) {
            double totalOrderAmount = client.getOrders().stream()
                    .mapToDouble(Orders::getTotalAmount)
                    .sum();

            model.addAttribute("client", client);
            model.addAttribute("totalOrderAmount", totalOrderAmount);

            return "profile";
        } else {
            return "redirect:/error";
        }
    }
    @PostMapping("/editProfile")
    public ResponseEntity<String> editProfile(
            @RequestParam String newName,
            @RequestParam String newSurname,
            @RequestParam String newMiddlename,
            @RequestParam String newLogin,
            @RequestParam String newEmail,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails
    ) {
        String username = userDetails.getUsername();
        Client client = clientRepository.findByLogin(username);

        if (client != null) {
            client.setName(newName);
            client.setSurname(newSurname);
            client.setMiddlename(newMiddlename);
            client.setLogin(newLogin);
            client.setEmail(newEmail);

            clientRepository.save(client);

            return ResponseEntity.ok("redirect:/profile");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}