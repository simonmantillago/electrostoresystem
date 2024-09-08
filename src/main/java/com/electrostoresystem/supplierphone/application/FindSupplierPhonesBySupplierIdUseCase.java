package com.electrostoresystem.supplierphone.application;

import java.util.List;

import com.electrostoresystem.supplierphone.domain.entity.SupplierPhone;
import com.electrostoresystem.supplierphone.domain.service.SupplierPhoneService;

public class FindSupplierPhonesBySupplierIdUseCase {
    private final SupplierPhoneService supplierPhoneService;

    public FindSupplierPhonesBySupplierIdUseCase(SupplierPhoneService supplierPhoneService) {
        this.supplierPhoneService = supplierPhoneService;
    }

    public List<SupplierPhone> execute(String supplierId) {
        return supplierPhoneService.findSupplierPhonesBySupplierId(supplierId);
    }
}
