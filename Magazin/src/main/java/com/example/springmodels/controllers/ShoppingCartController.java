package com.example.springmodels.controllers;

import com.example.springmodels.Service.ProductService;
import com.example.springmodels.Service.ShoppingCartService;
import com.example.springmodels.models.Client;
import com.example.springmodels.models.Product;
import com.example.springmodels.models.ShoppingCart;
import com.example.springmodels.repos.ClientRepository;
import com.example.springmodels.repos.ProductRepository;
import com.example.springmodels.repos.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
public class ShoppingCartController {
    public static   String username;
    private final ShoppingCartService shoppingCartService;
    private final ClientRepository clientRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductService productService;
    private final ProductRepository productRepository;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService, ClientRepository clientRepository, ShoppingCartRepository shoppingCartRepository, ProductRepository productRepository, ProductService productService) {
        this.shoppingCartService = shoppingCartService;
        this.clientRepository = clientRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.productRepository = productRepository;
        this.productService = productService;

    }

    @GetMapping("/cart")
    public String viewCart(Model model, @AuthenticationPrincipal Client client, Principal principal, HttpSession session) {
         username = principal.getName();
        client = clientRepository.findByLogin(username);

        List<ShoppingCart> cartItems = shoppingCartService.getCartItems(client.getId());

        int totalQuantity = 0;
        double totalAmount = 0.0;

        for (ShoppingCart cartItem : cartItems) {
            totalQuantity++;
            totalAmount += cartItem.getProductPrice();
        }

        String formattedTotalAmount = String.format("%.2f", totalAmount);

        session.setAttribute("totalQuantity", totalQuantity);
        session.setAttribute("totalAmount", formattedTotalAmount.replace(",", "."));

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalQuantity", totalQuantity);
        model.addAttribute("totalAmount", formattedTotalAmount.replace(",", "."));

        return "cart";
    }

    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam Long productId, Principal principal, Model model) {
        String username = principal.getName();

        Client client = clientRepository.findByLogin(username);

        if (client != null) {
            Product product = productService.getProductById(productId);

            if (product != null) {
                shoppingCartService.addToCart(productId, client.getId(), product.getName(), product.getPrice());

                return "redirect:/catalog";
            }
            else {
                return "redirect:/error";
            }
        }
        else {
            return "redirect:/error";
        }
    }

    @PostMapping("/remove-from-cart")
    public String removeFromCart(@RequestParam Long cartItemId, Principal principal) {
        try {
            String username = principal.getName();
            Client client = clientRepository.findByLogin(username);

            if (client != null) {
                shoppingCartService.deleteFromCart(cartItemId, client.getId());
                return "redirect:/cart";
            }
            else {
                return "redirect:/error";
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return "/cart";
        }
    }
    @PostMapping("/clear-cart")
    public String clearCart(Principal principal) {
        String username = principal.getName();
        Client client = clientRepository.findByLogin(username);

        if (client != null) {
            shoppingCartService.clearCart(client.getId());
        }

        return "redirect:/cart";
    }
}