package com.example.administratorpanel.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class PageController {

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/admin-page")
    public String showAdminPage() {
        return "admin-page";
    }

    @PreAuthorize("hasAnyAuthority('MANAGER')")
    @GetMapping("/manager-page")
    public String showManagerPage() {
        return "manager-page";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/operations")
    public String showOperationsPage() {
        return "operations";
    }

    @GetMapping("/backup/create")
    public ResponseEntity<String> createBackup(@RequestParam(value = "backupPath", required = false) String backupPath) {
        try {
            if (backupPath == null || backupPath.isEmpty()) {
                return ResponseEntity.status(400).body("Не указан путь для сохранения резервной копии");
            }

            // Имя файла резервной копии с текущей датой
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String backupFileName = "backup_" + timestamp + ".sql";

            // Команда для выполнения резервного копирования
            String command = String.format("C:\\Program Files\\PostgreSQL\\14\\bin\\pg_dump -U postgres -h localhost -p 5432 -d buildcomp -f D:\\backups\\%s", backupFileName);

            // Выполнение команды с использованием ProcessBuilder
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
            processBuilder.environment().put("PGPASSWORD", "1234");
            Process process = processBuilder.start();

            System.out.println("Executing command: " + command);

            // Получение кода завершения
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                return ResponseEntity.ok("Резервное копирование успешно создано: " + backupFileName);
            } else {
                return ResponseEntity.status(500).body("Ошибка при создании резервной копии");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            System.out.println("Error message: " + e.getMessage());
            return ResponseEntity.status(500).body("Ошибка при создании резервной копии");
        }
    }

}