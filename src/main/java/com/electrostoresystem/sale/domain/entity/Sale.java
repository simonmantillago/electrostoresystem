package com.electrostoresystem.sale.domain.entity;

public class Sale {
    private int id;
    private String date;
    private String clientId;
    private float total;
    private int payMethod;
    private float discountAmount;
    private float discountPercent;
    private int statusId;
    
    public Sale() {
    }

    public Sale(int id, String date, String clientId, float total, int payMethod, float discountAmount,
            float discountPercent, int statusId) {
        this.id = id;
        this.date = date;
        this.clientId = clientId;
        this.total = total;
        this.payMethod = payMethod;
        this.discountAmount = discountAmount;
        this.discountPercent = discountPercent;
        this.statusId = statusId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(int payMethod) {
        this.payMethod = payMethod;
    }

    public float getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(float discountAmount) {
        this.discountAmount = discountAmount;
    }

    public float getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(float discountPercent) {
        this.discountPercent = discountPercent;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    
    


    
}
