package com.example.springmodels.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

@Controller
public class PaymentController {

    @GetMapping("/payment")
    public String showPaymentPage(Model model, HttpSession session) {
        int totalQuantity = (int) session.getAttribute("totalQuantity");
        String totalAmountString = (String) session.getAttribute("totalAmount");

        model.addAttribute("totalQuantity", totalQuantity);
        model.addAttribute("totalAmount", totalAmountString.replace(",", "."));

        session.setAttribute("totalAmountBigDecimal", new BigDecimal(totalAmountString.replace(",", ".")));

        return "payment";
    }

    @GetMapping("/main_page")
    public String showMainPage(Model model) {
        return "main";
    }


}
