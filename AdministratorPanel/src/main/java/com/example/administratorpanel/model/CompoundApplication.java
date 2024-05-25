package com.example.administratorpanel.model;

import javax.persistence.*;

@Entity
@Table(name = "Compound_Application")
public class CompoundApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_Compound_Application")
    private Long id;

    @Column(name = "Quantity_Items")
    private int quantityItems;

    @ManyToOne
    @JoinColumn(name = "ID_Product")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "ID_Application")
    private Application app;

    // Геттеры и сеттеры

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantityItems() {
        return quantityItems;
    }

    public void setQuantityItems(int quantityItems) {
        this.quantityItems = quantityItems;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Application getApp() {
        return app;
    }

    public void setApp(Application app) {
        this.app = app;
    }
}
