package com.electrostoresystem.supplier.domain.entity;

public class Supplier {
    private String id;
    private String name;
    private String email;
    private int cityId ;
    private String addressDetails;

    public Supplier() {
    }

    public Supplier(String id, String name, String email, int cityId,String addressDetails) {
        this.id = id;
        this.name = name;
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
