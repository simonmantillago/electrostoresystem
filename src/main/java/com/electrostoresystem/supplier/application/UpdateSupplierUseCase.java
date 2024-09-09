package com.electrostoresystem.supplier.application;

import com.electrostoresystem.supplier.domain.entity.Supplier;
import com.electrostoresystem.supplier.domain.service.SupplierService;

public class UpdateSupplierUseCase {
 private final SupplierService supplierService;

    public UpdateSupplierUseCase(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    public void execute(Supplier supplier){
        supplierService.updateSupplier(supplier);
    }
}
 