package com.driver;

import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class DelivaryRepo {
    HashMap<String,DeliveryPartner>delivaryDB=new HashMap<>();

    public void addPartner(String partnerId) {
        delivaryDB.put(partnerId,new DeliveryPartner(partnerId));
    }

    public DeliveryPartner getPartnerByyId(String partnerId) {
        return delivaryDB.getOrDefault(partnerId,null);
    }
}
