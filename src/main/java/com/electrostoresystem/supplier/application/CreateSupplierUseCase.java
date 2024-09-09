package com.electrostoresystem.supplier.application;

import com.electrostoresystem.supplier.domain.entity.Supplier;
import com.electrostoresystem.supplier.domain.service.SupplierService;

public class CreateSupplierUseCase {
    private final SupplierService supplierService;

    public CreateSupplierUseCase(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    public void execute(Supplier supplier){
        supplierService.createSupplier(supplier);
    }

}
