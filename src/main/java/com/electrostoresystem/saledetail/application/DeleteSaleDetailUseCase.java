package com.electrostoresystem.saledetail.application;

import com.electrostoresystem.saledetail.domain.entity.SaleDetail;
import com.electrostoresystem.saledetail.domain.service.SaleDetailService;

public class DeleteSaleDetailUseCase {
    private final SaleDetailService saleDetailService;

    public DeleteSaleDetailUseCase(SaleDetailService saleDetailService) {
        this.saleDetailService = saleDetailService;
    }

    public SaleDetail execute(int codeSaleDetail) {
        return saleDetailService.deleteSaleDetail(codeSaleDetail);
    }
}
