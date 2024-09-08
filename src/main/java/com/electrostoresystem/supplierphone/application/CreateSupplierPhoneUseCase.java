package com.electrostoresystem.supplierphone.application;

import com.electrostoresystem.supplierphone.domain.entity.SupplierPhone;
import com.electrostoresystem.supplierphone.domain.service.SupplierPhoneService;

public class CreateSupplierPhoneUseCase {
private final SupplierPhoneService supplierPhoneService;

    public CreateSupplierPhoneUseCase(SupplierPhoneService supplierPhoneService) {
        this.supplierPhoneService = supplierPhoneService;
    }

    public void execute(SupplierPhone supplierPhone) {
        supplierPhoneService.createSupplierPhone(supplierPhone);
    }
}
