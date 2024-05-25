package com.example.springmodels.Service;

import com.example.springmodels.models.Favorite;
import com.example.springmodels.models.Product;
import com.example.springmodels.repos.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final ProductService productService;


    @Autowired
    public FavoriteService(FavoriteRepository favoriteRepository , ProductService productService) {
        this.favoriteRepository = favoriteRepository;
        this.productService = productService;
    }

    public void addToCart(Long productId, Long userId, String productName, Double productPrice) {
        Favorite cartItem = favoriteRepository.findByProductIdAndUserId(productId, userId);

        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        } else {
            cartItem = new Favorite();
            cartItem.setProductId(productId);
            cartItem.setUserId(userId);
            cartItem.setProductName(productName);
            cartItem.setProductPrice(productPrice);
            cartItem.setQuantity(1);
        }

        favoriteRepository.save(cartItem);
    }

    public List<Favorite> getCartItems(Long userId) {
        List<Favorite> cartItems = favoriteRepository.findByUserId(userId);

        for (Favorite cartItem : cartItems) {
            Product product = productService.getProductById(cartItem.getProductId());
            cartItem.setProductName(product.getName());
            cartItem.setProductPrice(product.getPrice());
            cartItem.setQuantity(cartItem.getQuantity());
        }

        return cartItems;
    }

    public void deleteFromCart(Long productId, Long userId) {
        Favorite cartItem = favoriteRepository.findByProductIdAndUserId(productId, userId);

        if (cartItem != null) {
            favoriteRepository.delete(cartItem);
        }
    }

    public void clearCart(Long userId) {
        List<Favorite> cartItems = favoriteRepository.findByUserId(userId);

        for (Favorite cartItem : cartItems) {
            favoriteRepository.delete(cartItem);
        }
    }
}