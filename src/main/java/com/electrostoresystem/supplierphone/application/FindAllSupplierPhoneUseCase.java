package com.electrostoresystem.supplierphone.application;

import java.util.List;

import com.electrostoresystem.supplierphone.domain.entity.SupplierPhone;
import com.electrostoresystem.supplierphone.domain.service.SupplierPhoneService;


public class FindAllSupplierPhoneUseCase {
    private final SupplierPhoneService supplierPhoneService;

    public FindAllSupplierPhoneUseCase(SupplierPhoneService supplierPhoneService) {
        this.supplierPhoneService = supplierPhoneService;
    }

    public List<SupplierPhone> execute() {
        return supplierPhoneService.findAllSupplierPhone();
    }
}
