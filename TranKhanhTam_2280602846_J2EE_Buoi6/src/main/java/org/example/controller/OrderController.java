package org.example.controller;

import org.example.model.Product;
import org.example.service.OrderService;
import org.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @GetMapping
    public String showOrderPage(@RequestParam("productId") int id, Model model) {
        Product product = productService.getProductById(id);
        if (product != null) {
            model.addAttribute("selectedProduct", product);
        }
        return "product/order/form";
    }

    @PostMapping("/submit")
    public String submitOrder(@RequestParam("productId") int productId,
                              @RequestParam("notes") String notes,
                              @RequestParam(value = "customerName", required = false) String customerName) {

        // Lấy thông tin người dùng hiện tại từ SecurityContext
        String username = org.springframework.security.core.context.SecurityContextHolder
                .getContext().getAuthentication().getName();

        List<Integer> productIds = Arrays.asList(productId);
        orderService.placeOrder(productIds, notes, username);

        return "redirect:/products?orderSuccess";
    }
}