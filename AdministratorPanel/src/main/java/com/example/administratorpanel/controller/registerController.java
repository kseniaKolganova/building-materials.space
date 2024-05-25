package com.example.administratorpanel.controller;

import com.example.administratorpanel.model.Employee;
import com.example.administratorpanel.model.RoleEnum;
import com.example.administratorpanel.repo.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
public class registerController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/registration")
    private String regView() {
        return "registration";
    }

    @PostMapping("/registration")
    private String reg(Employee employee, Model model) {
        Employee employeeFromDb = employeeRepository.findByLogin(employee.getLogin());
        if (employeeFromDb != null) {
            model.addAttribute("message", "Пользователь с таким логином уже существует");
            return "registration";
        }

        employee.setActive(true);
        employee.setRoles(Collections.singleton(RoleEnum.MANAGER));
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        employeeRepository.save(employee);
        return "redirect:/login";
    }
}
