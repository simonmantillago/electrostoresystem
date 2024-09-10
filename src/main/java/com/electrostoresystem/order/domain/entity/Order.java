package com.electrostoresystem.order.domain.entity;

public class Order {
    private int id;
    private String date;
    private String supplierId;
    private int statusId;
    private int payMethod;
    private float total;
    
    public Order() {
    }
    
    public Order(int id, String date, String supplierId, int statusId, int payMethod, float total) {
        this.id = id;
        this.date = date;
        this.supplierId = supplierId;
        this.statusId = statusId;
        this.payMethod = payMethod;
        this.total = total;
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

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(int payMethod) {
        this.payMethod = payMethod;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    

    
}
