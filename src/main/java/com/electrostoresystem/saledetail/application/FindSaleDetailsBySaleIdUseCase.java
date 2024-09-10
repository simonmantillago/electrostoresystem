package com.electrostoresystem.saledetail.application;

import java.util.List;

import com.electrostoresystem.saledetail.domain.entity.SaleDetail;
import com.electrostoresystem.saledetail.domain.service.SaleDetailService;

public class FindSaleDetailsBySaleIdUseCase {
    private final SaleDetailService saleDetailService;

    public FindSaleDetailsBySaleIdUseCase(SaleDetailService saleDetailService) {
        this.saleDetailService = saleDetailService;
    }

    public List<SaleDetail> execute(int saleId) {
        return saleDetailService.findSaleDetailsBySaleId(saleId);
    }
}
