package com.electrostoresystem.saledetail.application;

import java.util.Optional;

import com.electrostoresystem.saledetail.domain.entity.SaleDetail;
import com.electrostoresystem.saledetail.domain.service.SaleDetailService;

public class FindSaleDetailByIdUseCase {
    private final SaleDetailService saleDetailService;

    public FindSaleDetailByIdUseCase(SaleDetailService saleDetailService) {
        this.saleDetailService = saleDetailService;
    }

    public Optional<SaleDetail> execute(int id) {
        return saleDetailService.findSaleDetailById(id);
    }
}
