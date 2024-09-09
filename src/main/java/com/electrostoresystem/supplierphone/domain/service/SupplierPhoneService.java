package com.electrostoresystem.supplierphone.domain.service;

import java.util.List;
import java.util.Optional;




import com.electrostoresystem.supplierphone.domain.entity.SupplierPhone;

public interface SupplierPhoneService {
    void createSupplierPhone (SupplierPhone supplierPhone);
    void updateSupplierPhone (SupplierPhone supplierPhone, String originalPhone);
    SupplierPhone deleteSupplierPhone (String Phone);
    Optional<SupplierPhone> findSupplierPhoneByPhone(String Phone);
    List<SupplierPhone> findAllSupplierPhone();
    List<SupplierPhone> findSupplierPhonesBySupplierId(String supplierId);
    void deleteSupplierPhonesBySupplierId(String supplierId);
}
