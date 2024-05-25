package com.example.springmodels.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Order_Status")
public class OrderStatus {
    @Id
    @Column(name = "ID_Order_Status")
    private int id;

    @Column(name = "Name", nullable = false)
    private String name;

    // Геттеры и сеттеры

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


