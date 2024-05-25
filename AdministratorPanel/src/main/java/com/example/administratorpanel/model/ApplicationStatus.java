package com.example.administratorpanel.model;

import javax.persistence.*;

@Entity
@Table(name = "Application_Status")
public class ApplicationStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_Application_Status")
    private Long id;

    @Column(name = "Name", nullable = false)
    private String name;

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
}

