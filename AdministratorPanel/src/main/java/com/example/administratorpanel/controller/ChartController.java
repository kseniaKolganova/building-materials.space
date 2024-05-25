package com.example.administratorpanel.controller;

import com.example.administratorpanel.model.Orders;
import com.example.administratorpanel.repo.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ChartController {

    @Autowired
    private OrdersRepository ordersRepository;

    @GetMapping("/order-count-chart")
    public String showOrderCountChart(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            Model model
    ) {
        // Если даты не указаны, устанавливаем диапазон на последние 30 дней
        if (startDate == null || endDate == null) {
            Calendar calendar = Calendar.getInstance();
            endDate = calendar.getTime();
            calendar.add(Calendar.DAY_OF_MONTH, -30);
            startDate = calendar.getTime();
        }

        List<Orders> ordersList = ordersRepository.findByOrderDateBetween(startDate, endDate);
        Map<String, Integer> orderCountByDate = new HashMap<>();

        // Подготавливаем данные для графика
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (Orders order : ordersList) {
            String formattedDate = dateFormat.format(order.getOrderDate());
            orderCountByDate.put(formattedDate, orderCountByDate.getOrDefault(formattedDate, 0) + 1);
        }

        // Передаем данные в представление
        model.addAttribute("labels", new ArrayList<>(orderCountByDate.keySet()));
        model.addAttribute("data", new ArrayList<>(orderCountByDate.values()));
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return "order-count-chart";
    }
}
