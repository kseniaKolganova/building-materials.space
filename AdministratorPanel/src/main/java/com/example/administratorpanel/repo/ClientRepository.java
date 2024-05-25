package com.example.administratorpanel.repo;

import com.example.administratorpanel.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByLogin(String login);
}
