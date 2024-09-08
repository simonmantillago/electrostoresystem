package com.electrostoresystem.supplierphone.domain.entity;

public class SupplierPhone {
    private String supplierId;
    private String phone;
    
    public SupplierPhone() {
    }

    public SupplierPhone(String supplierId, String phone) {
        this.supplierId = supplierId;
        this.phone = phone;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    
}
