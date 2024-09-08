package com.electrostoresystem.supplierphone.application;

import com.electrostoresystem.supplierphone.domain.entity.SupplierPhone;
import com.electrostoresystem.supplierphone.domain.service.SupplierPhoneService;

public class UpdateSupplierPhoneUseCase {
    private final SupplierPhoneService supplierPhoneService;

    public UpdateSupplierPhoneUseCase(SupplierPhoneService supplierPhoneService) {
        this.supplierPhoneService = supplierPhoneService;
    }

    public void execute(SupplierPhone supplierPhone, String phone){
        supplierPhoneService.updateSupplierPhone(supplierPhone,phone);
    }
}
