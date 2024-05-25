package com.example.administratorpanel.model;

import javax.persistence.*;

@Entity
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "product_id")
    private Long productId;
    private Long userId;
    @ManyToOne
    @JoinColumn(name = "product_id_fk")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders order;

    // Геттер для продукта в корзине
    public Product getProduct() {
        return product;
    }
    private String productName;
    private Double productPrice;

    // Геттеры и сеттеры для productName и productPrice
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductName() {
        return productName;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    private Integer quantity;

    //Геттеры и сеттеры для quantity
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}