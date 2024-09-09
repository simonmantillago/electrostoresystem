package com.electrostoresystem.supplier.application;

import com.electrostoresystem.supplier.domain.entity.Supplier;
import com.electrostoresystem.supplier.domain.service.SupplierService;

public class DeleteSupplierUseCase {
    private final SupplierService supplierService;

    public DeleteSupplierUseCase(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    public Supplier execute(String codeSupplier) {
        return supplierService.deleteSupplier(codeSupplier);
    }
}
