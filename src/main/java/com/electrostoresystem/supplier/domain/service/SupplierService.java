package com.electrostoresystem.supplier.domain.service;

import java.util.List;
import java.util.Optional;

import com.electrostoresystem.supplier.domain.entity.Supplier;

public interface SupplierService {
    void createSupplier (Supplier supplier);
    void updateSupplier (Supplier supplier);
    Supplier deleteSupplier (String id);
    Optional<Supplier> findSupplierById(String id);
    List<Supplier> findAllSupplier();
}
