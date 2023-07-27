package com.driver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("orders")
public class OrderController {
HashMap<String,List<String>>PartnerOrderDB=new HashMap<>();
HashMap<String,String>OrderPartnerDB=new HashMap<>();
@Autowired
private OrderService osObj;
private DelivaryService dpsObj;
    @PostMapping("/add-order")
    public ResponseEntity<String> addOrder(@RequestBody Order order){
        osObj.addOrder(order);
        System.out.print(order.getId());
        return new ResponseEntity<>("New order added successfully", HttpStatus.CREATED);
    }
    @GetMapping("/test")
    public String test(){
        return "test Sucess";
    }


    @PostMapping("/add-partner/{partnerId}")
    public ResponseEntity<String> addPartner(@PathVariable String partnerId){
        dpsObj.addPartner(partnerId);
        return new ResponseEntity<>("New delivery partner added successfully", HttpStatus.CREATED);
    }

    @PutMapping("/add-order-partner-pair")
    public ResponseEntity<String> addOrderPartnerPair(@RequestParam String orderId, @RequestParam String partnerId){

        //This is basically assigning that order to that partnerId
        List<String>orders=PartnerOrderDB.getOrDefault(partnerId,new ArrayList<>());
        orders.add(orderId);
        PartnerOrderDB.put(partnerId,orders);
        OrderPartnerDB.put(orderId,partnerId);

        return new ResponseEntity<>("New order-partner pair added successfully", HttpStatus.CREATED);
    }

    @GetMapping("/get-order-by-id/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable String orderId){

        Order order= null;
        //order should be returned with an orderId.
        order=osObj.getOrderById(orderId);

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping("/get-partner-by-id/{partnerId}")
    public ResponseEntity<DeliveryPartner> getPartnerById(@PathVariable String partnerId){

        DeliveryPartner deliveryPartner = null;

        //deliveryPartner should contain the value given by partnerId
        deliveryPartner=dpsObj.getPartnerById(partnerId);

        return new ResponseEntity<>(deliveryPartner, HttpStatus.CREATED);
    }

    @GetMapping("/get-order-count-by-partner-id/{partnerId}")
    public ResponseEntity<Integer> getOrderCountByPartnerId(@PathVariable String partnerId){

        Integer orderCount = 0;

        //orderCount should denote the orders given by a partner-id
        orderCount=PartnerOrderDB.getOrDefault(partnerId,new ArrayList<>()).size();

        return new ResponseEntity<>(orderCount, HttpStatus.CREATED);
    }

    @GetMapping("/get-orders-by-partner-id/{partnerId}")
    public ResponseEntity<List<String>> getOrdersByPartnerId(@PathVariable String partnerId){
        List<String> orders = null;

        //orders should contain a list of orders by PartnerId
        orders=PartnerOrderDB.getOrDefault(partnerId,new ArrayList<>());
        return new ResponseEntity<>(orders, HttpStatus.CREATED);
    }

    @GetMapping("/get-all-orders")
    public ResponseEntity<List<String>> getAllOrders(){
        List<String> orders = null;

        //Get all orders
        orders=osObj.getAllOrders();

        return new ResponseEntity<>(orders, HttpStatus.CREATED);
    }

    @GetMapping("/get-count-of-unassigned-orders")
    public ResponseEntity<Integer> getCountOfUnassignedOrders(){
        Integer countOfOrders = 0;

        //Count of orders that have not been assigned to any DeliveryPartner
        Integer allorder=osObj.getOrders();
        Integer assiged=OrderPartnerDB.size();
        countOfOrders=allorder-assiged;

        return new ResponseEntity<>(countOfOrders, HttpStatus.CREATED);
    }

    @GetMapping("/get-count-of-orders-left-after-given-time/{partnerId}")
    public ResponseEntity<Integer> getOrdersLeftAfterGivenTimeByPartnerId(@PathVariable String time, @PathVariable String partnerId){

        Integer countOfOrders = 0;
        int t=Integer.parseInt(time.substring(0,2))*60+Integer.parseInt(time.substring(3,5));


        //countOfOrders that are left after a particular time of a DeliveryPartner
        List<String>orders=PartnerOrderDB.getOrDefault(partnerId,new ArrayList<>());
            for(String id:orders){
                if(osObj.getOrderById(id).getDeliveryTime()>t){
                    countOfOrders+=1;
                }
            }

        return new ResponseEntity<>(countOfOrders, HttpStatus.CREATED);
    }

    @GetMapping("/get-last-delivery-time/{partnerId}")
    public ResponseEntity<String> getLastDeliveryTimeByPartnerId(@PathVariable String partnerId){
        String time = null;

        //Return the time when that partnerId will deliver his last delivery order.
        List<String>orders=PartnerOrderDB.getOrDefault(partnerId,new ArrayList<>());
        String orderid=orders.get(orders.size()-1);
        int t=osObj.getOrderById(orderid).getDeliveryTime();
        int mm=t%60;
        int hh=t/60;
        time=Integer.toString(hh)+":"+Integer.toString(mm);

        return new ResponseEntity<>(time, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-partner-by-id/{partnerId}")
    public ResponseEntity<String> deletePartnerById(@PathVariable String partnerId){

        //Delete the partnerId
        //And push all his assigned orders to unassigned orders.
        List<String>orders=PartnerOrderDB.getOrDefault(partnerId,new ArrayList<>());
        PartnerOrderDB.remove(partnerId);
        for(String id:orders){
            OrderPartnerDB.remove(id);
        }

        return new ResponseEntity<>(partnerId + " removed successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-order-by-id/{orderId}")
    public ResponseEntity<String> deleteOrderById(@PathVariable String orderId){

        //Delete an order and also
        // remove it from the assigned order of that partnerId
        String pid=OrderPartnerDB.get(orderId);
        OrderPartnerDB.remove(orderId);
        List<String>orders=PartnerOrderDB.get(pid);
        orders.remove(orderId);
        PartnerOrderDB.put(pid,orders);
        return new ResponseEntity<>(orderId + " removed successfully", HttpStatus.CREATED);
    }
}
