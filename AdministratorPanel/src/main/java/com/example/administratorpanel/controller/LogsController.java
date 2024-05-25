package com.example.administratorpanel.controller;

import com.example.administratorpanel.model.Employee;
import com.example.administratorpanel.model.Logs;
import com.example.administratorpanel.repo.EmployeeRepository;
import com.example.administratorpanel.repo.LogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/logs")
public class LogsController {

    private final LogsRepository logsRepository;

    @Autowired
    public LogsController(LogsRepository logsRepository) {
        this.logsRepository = logsRepository;
    }

    // Метод для отображения всех сотрудников
    @GetMapping()
    public String showAllLogs(Model model) {
        List<Logs> logs = (List<Logs>) logsRepository.findAll();
        model.addAttribute("logs", logs);
        return "logs";
    }
}
