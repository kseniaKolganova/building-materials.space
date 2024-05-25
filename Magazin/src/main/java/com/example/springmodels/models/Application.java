//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.springmodels.models;

import javax.persistence.*;
import java.security.Timestamp;

@Entity
@Table(
        name = "Application"
)
public class Application {
    @Id
    @Column(
            name = "ID_Application"
    )
    private int id;
    @Column(
            name = "Number",
            unique = true,
            nullable = false
    )
    private int number;
    @Column(
            name = "Date_Create",
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
    )
    private Timestamp dateCreate;
    @ManyToOne
    @JoinColumn(
            name = "ID_Application_Status"
    )
    private ApplicationStatus applicationStatus;
    @ManyToOne
    @JoinColumn(
            name = "ID_Suppler"
    )
    private Suppler suppler;

    public Application() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Timestamp getDateCreate() {
        return this.dateCreate;
    }

    public void setDateCreate(Timestamp dateCreate) {
        this.dateCreate = dateCreate;
    }

    public ApplicationStatus getApplicationStatus() {
        return this.applicationStatus;
    }

    public void setApplicationStatus(ApplicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public Suppler getSuppler() {
        return this.suppler;
    }

    public void setSuppler(Suppler suppler) {
        this.suppler = suppler;
    }
}
