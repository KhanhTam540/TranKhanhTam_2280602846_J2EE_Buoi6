package org.example.service;

import org.example.model.Order;
import org.example.model.Product;
import org.example.repository.OrderRepository;
import org.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public void placeOrder(List<Integer> productIds, String notes, String customerName) {
        Order order = new Order();
        order.setCustomerName(customerName);
        order.setNotes(notes);
        order.setOrderDate(LocalDateTime.now());

        List<Product> products = productRepository.findAllById(productIds);
        order.setProducts(products);

        orderRepository.save(order);
    }
}