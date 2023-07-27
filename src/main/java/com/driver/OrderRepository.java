package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class OrderRepository {
    HashMap<String,Order>orderDB=new HashMap<>();

    public void addOrder(Order order) {
        String id=order.getId();
        orderDB.put(id,order);
    }

    public Order getOrderById(String orderId) {
        return orderDB.getOrDefault(orderId,null);
    }

    public List<String> getAllOrders() {
        List<String>ans=new ArrayList<>();
        for(String key:orderDB.keySet()){
            ans.add(key);
        }
        return ans;
    }

    public Integer getOrders() {
        return orderDB.size();
    }
}
