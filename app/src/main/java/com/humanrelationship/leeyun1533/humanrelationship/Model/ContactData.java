package com.humanrelationship.leeyun1533.humanrelationship.Model;

import java.io.Serializable;

public class ContactData implements Serializable {
    private String phoneNumber, name;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
