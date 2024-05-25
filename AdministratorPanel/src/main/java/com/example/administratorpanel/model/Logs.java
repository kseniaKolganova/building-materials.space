package com.example.administratorpanel.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Logs")
public class Logs {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_Logs")
    private int id;

    @Column(name = "Data_Logs", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Timestamp dataLogs;

    @Column(name = "Action_Client", nullable = false)
    private String actionClient;

    // Геттеры и сеттеры

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDataLogs() {
        return dataLogs;
    }

    public void setDataLogs(Timestamp dataLogs) {
        this.dataLogs = dataLogs;
    }

    public String getActionClient() {
        return actionClient;
    }

    public void setActionClient(String actionClient) {
        this.actionClient = actionClient;
    }
}

