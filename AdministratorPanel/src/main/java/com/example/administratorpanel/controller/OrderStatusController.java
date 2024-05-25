package com.example.administratorpanel.controller;

import com.example.administratorpanel.model.OrderStatus;
import com.example.administratorpanel.repo.OrdersStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.util.List;

@Controller
@RequestMapping("/orderStatuses")
public class OrderStatusController {

    private final OrdersStatusRepository orderStatusRepository;

    @Autowired
    public OrderStatusController(OrdersStatusRepository orderStatusRepository) {
        this.orderStatusRepository = orderStatusRepository;
    }


    @GetMapping()
    public String listOrderStatuses(Model model) {
        List<OrderStatus> orderStatuses = (List<OrderStatus>) orderStatusRepository.findAll();
        model.addAttribute("orderStatuses", orderStatuses);
        return "orderStatuses/allOrderStatuses";
    }

    @GetMapping("/addOrderStatus")
    public String showAddOrderStatusForm(Model model) {
        OrderStatus orderStatus = new OrderStatus();
        model.addAttribute("orderStatus", orderStatus);
        return "orderStatuses/addOrderStatus";
    }

    @PostMapping("/addOrderStatus")
    public String addOrderStatus(@Valid @ModelAttribute OrderStatus orderStatus, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "orderStatuses/addOrderStatus";
        }
        orderStatusRepository.save(orderStatus);
        return "redirect:/orderStatuses";
    }

    @GetMapping("/editOrderStatus/{id}")
    public String showEditOrderStatusForm(@PathVariable Long id, Model model) {
        OrderStatus orderStatus = orderStatusRepository.findById(id).orElse(null);
        if (orderStatus == null) {
            return "redirect:/orderStatuses";
        }
        model.addAttribute("orderStatus", orderStatus);
        return "orderStatuses/editOrderStatus";
    }

    @PostMapping("/editOrderStatus/{id}")
    public String editOrderStatus(@PathVariable Long id, @Valid @ModelAttribute OrderStatus orderStatus, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "orderStatuses/editOrderStatus";
        }
        orderStatusRepository.save(orderStatus);
        return "redirect:/orderStatuses";
    }

    @GetMapping("/deleteOrderStatus/{id}")
    public String deleteOrderStatus(@PathVariable Long id) {
        try {
            orderStatusRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/orderStatuses";
    }
}
