package com.example.springmodels.Service;

import com.example.springmodels.models.Product;
import com.example.springmodels.models.ShoppingCart;
import com.example.springmodels.repos.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductService productService;


    @Autowired
    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ProductService productService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productService = productService;
    }

    public void addToCart(Long productId, Long userId, String productName, Double productPrice) {
        ShoppingCart cartItem = shoppingCartRepository.findByProductIdAndUserId(productId, userId);

        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        } else {
            cartItem = new ShoppingCart();
            cartItem.setProductId(productId);
            cartItem.setUserId(userId);
            cartItem.setProductName(productName);
            cartItem.setProductPrice(productPrice);
            cartItem.setQuantity(1);
        }
        shoppingCartRepository.save(cartItem);
    }

    public List<ShoppingCart> getCartItems(Long userId) {
        List<ShoppingCart> cartItems = shoppingCartRepository.findByUserId(userId);

        for (ShoppingCart cartItem : cartItems) {
            Product product = productService.getProductById(cartItem.getProductId());
            cartItem.setProductName(product.getName());
            cartItem.setProductPrice(product.getPrice());
            cartItem.setQuantity(cartItem.getQuantity());
        }

        return cartItems;
    }

    public void deleteFromCart(Long productId, Long userId) {
        ShoppingCart cartItem = shoppingCartRepository.findByProductIdAndUserId(productId, userId);

        if (cartItem != null) {
            shoppingCartRepository.delete(cartItem);
        }
    }

    public void clearCart(Long userId) {
        List<ShoppingCart> cartItems = shoppingCartRepository.findByUserId(userId);

        for (ShoppingCart cartItem : cartItems) {
            shoppingCartRepository.delete(cartItem);
        }
    }
}