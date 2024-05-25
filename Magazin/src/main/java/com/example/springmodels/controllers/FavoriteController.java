package com.example.springmodels.controllers;

import com.example.springmodels.Service.FavoriteService;
import com.example.springmodels.Service.ProductService;
import com.example.springmodels.models.Client;
import com.example.springmodels.models.Favorite;
import com.example.springmodels.models.Product;
import com.example.springmodels.repos.ClientRepository;
import com.example.springmodels.repos.FavoriteRepository;
import com.example.springmodels.repos.ProductRepository;
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

import static com.example.springmodels.controllers.ShoppingCartController.username;

@Controller
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final ClientRepository clientRepository;
    private final FavoriteRepository favoriteRepository;
    private final ProductService productService;
    private final ProductRepository productRepository;

    @Autowired
    public FavoriteController(FavoriteService favoriteService, ClientRepository clientRepository, FavoriteRepository favoriteRepository, ProductRepository productRepository, ProductService productService) {
        this.favoriteService = favoriteService;
        this.clientRepository = clientRepository;
        this.favoriteRepository = favoriteRepository;
        this.productRepository = productRepository;
        this.productService = productService;
    }


    @GetMapping("/favorite")
    public String viewCart(Model model, @AuthenticationPrincipal Client client, Principal principal, HttpSession session) {
        String username = principal.getName();
        client = clientRepository.findByLogin(username);

        List<Favorite> cartItems = favoriteService.getCartItems(client.getId());

        int totalQuantity = 0;
        double totalAmount = 0.0;

        for (Favorite cartItem : cartItems) {
            totalQuantity++;
            totalAmount += cartItem.getProductPrice();
        }

        String formattedTotalAmount = String.format("%.2f", totalAmount);

        session.setAttribute("totalQuantity", totalQuantity);
        session.setAttribute("totalAmount", formattedTotalAmount);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalQuantity", totalQuantity);
        model.addAttribute("totalAmount", formattedTotalAmount);

        return "favorite";
    }

    @PostMapping("/add-to-favorites")
    public String addToCart(@RequestParam Long productId, Principal principal, Model model) {
        username = principal.getName();

        Client client = clientRepository.findByLogin(username);

        if (client != null) {
            Product product = productService.getProductById(productId);

            if (product != null) {
                favoriteService.addToCart(productId, client.getId(), product.getName(), product.getPrice());

                return "redirect:/catalog";
            } else {
                return "redirect:/error";
            }
        } else {
            return "redirect:/error";
        }
    }

    @PostMapping("/remove-from-favorite")
    public String removeFromCart(@RequestParam Long cartItemId, Principal principal) {
        try {
            String username = principal.getName();
            Client client = clientRepository.findByLogin(username);

            if (client != null) {
                favoriteService.deleteFromCart(cartItemId, client.getId());
                return "redirect:/favorite";
            } else {
                return "redirect:/error";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "/favorite";
        }
    }

}
