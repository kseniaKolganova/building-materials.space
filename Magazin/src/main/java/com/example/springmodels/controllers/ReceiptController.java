package com.example.springmodels.controllers;

import com.example.springmodels.models.Client;
import com.example.springmodels.models.Orders;
import com.example.springmodels.models.ShoppingCart;
import com.example.springmodels.repos.ClientRepository;
import com.example.springmodels.repos.OrdersRepository;
import com.example.springmodels.repos.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ReceiptController {

    private final ClientRepository clientRepository;
    private final OrdersRepository orderRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    @Autowired
    public ReceiptController(ClientRepository clientRepository, OrdersRepository orderRepository, ShoppingCartRepository shoppingCartRepository) {
        this.clientRepository = clientRepository;
        this.orderRepository = orderRepository;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @GetMapping("/receipt/{orderId}")
    public String showReceipt(@PathVariable Long orderId,
                              @AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails,
                              Model model, HttpSession session) {
        String username = userDetails.getUsername();
        Client client = clientRepository.findByLogin(username);

        Orders order = orderRepository.findById(orderId).orElse(null);

        if (order != null && order.getClient().equals(client)) {
            model.addAttribute("order", order);

            List<String> productNames = getProductNames(order.getCartItems());
            model.addAttribute("productNames", productNames);

            BigDecimal totalAmount = (BigDecimal) session.getAttribute("totalAmountBigDecimal");

            model.addAttribute("totalAmount", totalAmount);
            return "receipt";
        } else {
            return "redirect:/error";
        }
    }

    private List<String> getProductNames(List<ShoppingCart> cartItems) {
        return cartItems.stream()
                .map(item -> item.getProduct().getName())
                .collect(Collectors.toList());
    }
}