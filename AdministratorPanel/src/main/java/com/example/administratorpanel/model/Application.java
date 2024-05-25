package com.example.administratorpanel.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import javax.persistence.*;

@Entity
@Table(name = "Application")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_Application")
    private Long id;

    @Column(name = "Number", nullable = false)
    private int number;

    @Column(name = "Date_Create", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Timestamp dateCreate;

    @ManyToOne
    @JoinColumn(name = "ID_Application_Status")
    private ApplicationStatus applicationStatus;

    @ManyToOne
    @JoinColumn(name = "ID_Suppler")
    private Suppler suppler;

    // Геттеры и сеттеры

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Timestamp getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Timestamp dateCreate) {
        this.dateCreate = dateCreate;
    }

    public ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(ApplicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
    }
    public Suppler getSuppler() {
        return suppler;
    }

    public void setSuppler(Suppler suppler) {
        this.suppler = suppler;
    }
}

