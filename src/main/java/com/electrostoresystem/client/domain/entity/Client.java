package com.electrostoresystem.client.domain.entity;

public class Client {
    private String id;
    private String name;
    private int typeId;
    private int clientTypeId;
    private String email;
    private int cityId ;
    private String addressDetails;
    
    public Client() {
    }

    public Client(String id, String name, int typeId, int clientTypeId, String email, int cityId, String addressDetails) {
        this.id = id;
        this.name = name;
        this.typeId = typeId;
        this.clientTypeId = clientTypeId;
        this.email = email;
        this.cityId = cityId;
        this.addressDetails = addressDetails;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getClientTypeId() {
        return clientTypeId;
    }

    public void setClientTypeId(int clientTypeId) {
        this.clientTypeId = clientTypeId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getAddressDetails() {
        return addressDetails;
    }

    public void setAddressDetails(String addressDetails) {
        this.addressDetails = addressDetails;
    }
    
    
}
