package com.example.administratorpanel.model;

import javax.persistence.*;

@Entity
@Table(name = "Suppler")
public class Suppler {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_Suppler")
    private Long id;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Address", nullable = false)
    private String address;

    @Column(name = "Email", nullable = false)
    private String email;

    @Column(name = "Phone_Number", unique = true, nullable = false)
    private String phoneNumber;

    @Column(name = "Delivery_Items", nullable = false)
    private String deliveryItems;

    // Геттеры и сеттеры

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDeliveryItems() {
        return deliveryItems;
    }

    public void setDeliveryItems(String deliveryItems) {
        this.deliveryItems = deliveryItems;
    }
}

