package com.electrostoresystem.supplierphone.application;

import com.electrostoresystem.supplierphone.domain.service.SupplierPhoneService;

public class DeleteSupplierPhonesBySupplierIdUseCase {
    private final SupplierPhoneService supplierPhoneService;

    public DeleteSupplierPhonesBySupplierIdUseCase(SupplierPhoneService supplierPhoneService) {
        this.supplierPhoneService = supplierPhoneService;
    }

    public void execute(String supplierId) {
        supplierPhoneService.deleteSupplierPhonesBySupplierId(supplierId);
    }
}