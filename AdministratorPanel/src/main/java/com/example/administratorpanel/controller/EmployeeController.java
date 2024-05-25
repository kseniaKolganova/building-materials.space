package com.example.administratorpanel.controller;

import com.example.administratorpanel.model.Employee;
import com.example.administratorpanel.model.RoleEnum;
import com.example.administratorpanel.repo.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Метод для отображения всех сотрудников
    @GetMapping()
    public String showAllEmployees(Model model) {
        List<Employee> employees = (List<Employee>) employeeRepository.findAll();
        model.addAttribute("employees", employees);
        return "employees/allEmployees";
    }

    @GetMapping("/addEmployee")
    public String showAddEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("allRoles", RoleEnum.values()); // Передаем все возможные роли в форму
        return "employees/addEmployee";
    }

    // Метод для обработки запроса на добавление нового сотрудника
    @PostMapping("/addEmployee")
    public String addEmployee(@ModelAttribute Employee employee, @RequestParam("role") RoleEnum role) {
        // Обработка пароля перед сохранением
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        // Дополнительная логика, если необходимо
        employee.setActive(true);
        employee.setRoles(Collections.singleton(role));

        employeeRepository.save(employee);
        return "redirect:/employees";
    }

    // Метод для отображения формы редактирования сотрудника
    @GetMapping("/editEmployee/{id}")
    public String showEditEmployeeForm(@PathVariable Long id, Model model) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));
        model.addAttribute("employee", employee);
        model.addAttribute("allRoles", RoleEnum.values()); // Передаем все возможные роли в форму
        return "employees/editEmployee";
    }

    // Метод для обработки запроса на редактирование сотрудника
    @PostMapping("/editEmployee/{id}")
    public String editEmployee(@PathVariable Long id, @ModelAttribute Employee employee, @RequestParam("role") RoleEnum role) {
        // Обработка пароля перед сохранением
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        // Дополнительная логика, если необходимо
        employee.setActive(true);
        employee.setRoles(Collections.singleton(role));

        employeeRepository.save(employee);
        return "redirect:/employees";
    }

    @GetMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        // Проверка наличия сотрудника с заданным ID
        if (employeeRepository.existsById(id)) {
            // Удаление сотрудника по ID
            employeeRepository.deleteById(id);
        }
        // Перенаправление на страницу со списком сотрудников
        return "redirect:/employees";
    }
}
