package com.electrostoresystem.saledetail.application;

import com.electrostoresystem.saledetail.domain.service.SaleDetailService;

public class DeleteSaleDetailsBySaleIdUseCase {
    private final SaleDetailService saleDetailService;

    public DeleteSaleDetailsBySaleIdUseCase(SaleDetailService saleDetailService) {
        this.saleDetailService = saleDetailService;
    }

    public void execute(int saleId) {
        saleDetailService.deleteSaleDetailsBySaleId(saleId);
    }
}