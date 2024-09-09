package com.electrostoresystem.supplier.application;

import java.util.List;

import com.electrostoresystem.supplier.domain.entity.Supplier;
import com.electrostoresystem.supplier.domain.service.SupplierService;

public class FindAllSupplierUseCase {
    private final SupplierService supplierService;

    public FindAllSupplierUseCase(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    public List<Supplier> execute() {
        return supplierService.findAllSupplier();
    }
}
