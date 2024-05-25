//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.springmodels.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(
        name = "Categories"
)
public class Categories {
    @Id
    @Column(
            name = "ID_Categories"
    )
    private int id;
    @Column(
            name = "Name",
            unique = true,
            nullable = false
    )
    private String name;

    public Categories() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
