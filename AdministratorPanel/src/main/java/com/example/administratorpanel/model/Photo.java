package com.example.administratorpanel.model;

import javax.persistence.*;

@Entity
@Table(name = "Photo")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_Photo")
    private Long id;

    @Column(name = "Url", length = 200, nullable = false)
    private String url;

    @ManyToOne
    @JoinColumn(name = "ID_Product", nullable = false)
    private Product product;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

