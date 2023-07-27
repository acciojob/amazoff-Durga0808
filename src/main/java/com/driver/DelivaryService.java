package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DelivaryService {
    @Autowired
    private DelivaryRepo dprObj;

    public void addPartner(String partnerId) {
        dprObj.addPartner(partnerId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return dprObj.getPartnerByyId(partnerId);
    }
}
