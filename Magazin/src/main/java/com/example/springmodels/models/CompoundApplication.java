package com.example.springmodels.models;

import javax.persistence.*;

@Entity
@Table(name = "Compound_Application")
public class CompoundApplication {
    @Id
    @Column(name = "ID_Compound_Application")
    private int id;

    @Column(name = "Quantity_Items")
    private int quantityItems;

    @ManyToOne
    @JoinColumn(name = "ID_Product")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "ID_Application")
    private Application application;

    // Геттеры и сеттеры

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
}

