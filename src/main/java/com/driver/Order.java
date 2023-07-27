package com.driver;

public class Order {

    private String id;
    private int deliveryTime;
    private String dtime;
    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        this.id=id;
        this.dtime=deliveryTime;

    }

    public void setDeliveryTime() {
        int hr=Integer.parseInt(dtime.substring(0,2))*60;
        int mm=Integer.parseInt(dtime.substring(3,5));
        this.deliveryTime=hr+mm;
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}
}
