package com.electrostoresystem.saledetail.application;

import com.electrostoresystem.saledetail.domain.entity.SaleDetail;
import com.electrostoresystem.saledetail.domain.service.SaleDetailService;

public class CreateSaleDetailUseCase {
    private final SaleDetailService saleDetailService;

    public CreateSaleDetailUseCase(SaleDetailService saleDetailService) {
        this.saleDetailService = saleDetailService;
    }

    public void execute(SaleDetail saleDetail){
        saleDetailService.createSaleDetail(saleDetail);
    }

}
