package com.example.administratorpanel.model;

import javax.persistence.*;

@Entity
@Table(name = "Post")
public class Post {
    @Id
    @Column(name = "ID_Post")
    private int id;

    @Column(name = "Name", unique = true, nullable = false)
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

