package com.electrostoresystem.supplier.application;

import java.util.Optional;

import com.electrostoresystem.supplier.domain.entity.Supplier;
import com.electrostoresystem.supplier.domain.service.SupplierService;

public class FindSupplierByIdUseCase {
    private final SupplierService supplierService;

    public FindSupplierByIdUseCase(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    public Optional<Supplier> execute(String id) {
        return supplierService.findSupplierById(id);
    }
}
