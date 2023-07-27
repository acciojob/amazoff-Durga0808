package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orObj;

    public void addOrder(Order order) {
        orObj.addOrder(order);
    }

    public Order getOrderById(String orderId) {
        return orObj.getOrderById(orderId);
    }

    public List<String> getAllOrders() {
        return orObj.getAllOrders();
    }

    public Integer getOrders() {
        return orObj.getOrders();
    }
}
