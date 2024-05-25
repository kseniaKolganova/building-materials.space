package com.example.administratorpanel.controller;

import com.example.administratorpanel.model.Client;
import com.example.administratorpanel.model.Orders;
import com.example.administratorpanel.repo.ClientRepository;
import com.example.administratorpanel.repo.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrdersController {

    private final OrdersRepository ordersRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public OrdersController(OrdersRepository ordersRepository, ClientRepository clientRepository) {
        this.ordersRepository = ordersRepository;
        this.clientRepository = clientRepository;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Client.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                Client client = clientRepository.findById(Long.parseLong(text)).orElse(null);
                setValue(client);
            }
        });

        binder.registerCustomEditor(Date.class, new TimestampEditor());
    }

    @GetMapping()
    public String listOrders(Model model) {
        List<Orders> orders = (List<Orders>) ordersRepository.findAll();
        model.addAttribute("orders", orders);
        return "orders/allOrders";
    }

    @GetMapping("/addOrder")
    public String showAddOrderForm(Model model) {
        Orders order = new Orders();
        List<Client> clients = (List<Client>) clientRepository.findAll();
        model.addAttribute("order", order);
        model.addAttribute("clients", clients);
        return "orders/addOrder";
    }

    @PostMapping("/addOrder")
    public String addOrder(@Valid @ModelAttribute("order") Orders order, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "orders/addOrder";
        }
        ordersRepository.save(order);
        return "redirect:/orders";
    }

    @GetMapping("/editOrder/{id}")
    public String showEditOrderForm(@PathVariable("id") Long id, Model model) {
        Orders order = ordersRepository.findById(id).orElse(null);
        List<Client> clients = (List<Client>) clientRepository.findAll();
        if (order == null) {
            return "redirect:/orders";
        }
        model.addAttribute("order", order);
        model.addAttribute("clients", clients);
        return "orders/editOrder";
    }

    @PostMapping("/editOrder/{id}")
    public String editOrder(@PathVariable("id") Long id, @Valid @ModelAttribute("order") Orders order, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "orders/editOrder";
        }
        order.setId(id);
        ordersRepository.save(order);
        return "redirect:/orders";
    }

    @GetMapping("/deleteOrder/{id}")
    public String deleteOrder(@PathVariable("id") Long id) {
        try {
            ordersRepository.deleteById(id);
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
        }
        return "redirect:/orders";
    }
}
