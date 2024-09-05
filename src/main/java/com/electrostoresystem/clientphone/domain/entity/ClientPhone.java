package com.electrostoresystem.clientphone.domain.entity;

public class ClientPhone {
    private String clientId;
    private String phone;
    
    public ClientPhone() {
    }

    public ClientPhone(String clientId, String phone) {
        this.clientId = clientId;
        this.phone = phone;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    
}
