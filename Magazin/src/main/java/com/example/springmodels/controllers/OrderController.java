package com.example.springmodels.controllers;

import com.example.springmodels.Service.ShoppingCartService;
import com.example.springmodels.models.Client;
import com.example.springmodels.models.Orders;
import com.example.springmodels.models.ShoppingCart;
import com.example.springmodels.repos.ClientRepository;
import com.example.springmodels.repos.OrdersRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrdersRepository orderRepository;
    private final ClientRepository clientRepository;

    private final ShoppingCartService shoppingCartService;
    @Autowired
    public OrderController(OrdersRepository orderRepository, ClientRepository clientRepository, ShoppingCartService shoppingCartService) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping("/create")
    public String createOrder(@ModelAttribute("orders") Orders order, Model model, Principal principal, HttpSession session) {
        order.setOrderDate(new Date());
        String username = principal.getName();

        Client client = clientRepository.findByLogin(username);

        BigDecimal totalAmount = (BigDecimal) session.getAttribute("totalAmountBigDecimal");
        order.setTotalAmount(totalAmount.doubleValue());
        order.setOrderNumber(generateOrderNumber());
        order.setUserId(client.getId());
        order.setClient(client);
        Orders savedOrder = orderRepository.save(order);

        clearCart(client.getId());
        return "redirect:/receipt/" + savedOrder.getId();
    }

    private void clearCart(Long userId) {
        List<ShoppingCart> cartItems = shoppingCartService.getCartItems(userId);

        generateReceipt(cartItems, userId);

        for (ShoppingCart cartItem : cartItems) {
            shoppingCartService.deleteFromCart(cartItem.getProductId(), userId);
        }
    }

    private void generateReceipt(List<ShoppingCart> cartItems, Long userId) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Receipt");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Product Name");
        headerRow.createCell(1).setCellValue("Product Price");
        headerRow.createCell(2).setCellValue("Quantity");

        int rowNum = 1;
        for (ShoppingCart cartItem : cartItems) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(cartItem.getProductName());
            row.createCell(1).setCellValue(cartItem.getProductPrice());
            row.createCell(2).setCellValue(cartItem.getQuantity());
        }

        try (FileOutputStream fileOut = new FileOutputStream("receipt_user_" + userId + ".xlsx")) {
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String generateOrderNumber() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String timestamp = dateFormat.format(date);

        Random random = new Random();
        int randomNumber = random.nextInt(10000);

        return "ORDERS-" + timestamp + "-" + randomNumber;
    }

}
