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
        name = "Application_Status"
)
public class ApplicationStatus {
    @Id
    @Column(
            name = "ID_Application_Status"
    )
    private int id;
    @Column(
            name = "Name",
            nullable = false
    )
    private String name;

    public ApplicationStatus() {
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
