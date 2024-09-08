package com.electrostoresystem.supplierphone.application;

import java.util.Optional;

import com.electrostoresystem.supplierphone.domain.entity.SupplierPhone;
import com.electrostoresystem.supplierphone.domain.service.SupplierPhoneService;

public class FindSupplierPhoneByPhoneUseCase {
    private final SupplierPhoneService supplierPhoneService;

    public FindSupplierPhoneByPhoneUseCase(SupplierPhoneService supplierPhoneService) {
        this.supplierPhoneService = supplierPhoneService;
    }

    public Optional<SupplierPhone> execute(String phone) {
        return supplierPhoneService.findSupplierPhoneByPhone(phone);
    }
}
