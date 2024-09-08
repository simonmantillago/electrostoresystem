package com.electrostoresystem.supplierphone.application;

import com.electrostoresystem.supplierphone.domain.entity.SupplierPhone;
import com.electrostoresystem.supplierphone.domain.service.SupplierPhoneService;

public class DeleteSupplierPhoneUseCase {
    private final SupplierPhoneService supplierPhoneService;

    public DeleteSupplierPhoneUseCase(SupplierPhoneService supplierPhoneService) {
        this.supplierPhoneService = supplierPhoneService;
    }

    public SupplierPhone execute(String Phone) {
        return supplierPhoneService.deleteSupplierPhone(Phone);
    }
}
