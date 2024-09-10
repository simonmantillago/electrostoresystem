package com.electrostoresystem.saledetail.application;

import com.electrostoresystem.saledetail.domain.entity.SaleDetail;
import com.electrostoresystem.saledetail.domain.service.SaleDetailService;

public class CreateSaleUseCase {
    private final SaleDetailService saleDetailService;

    public CreateSaleUseCase(SaleDetailService saleDetailService) {
        this.saleDetailService = saleDetailService;
    }

    public void execute(SaleDetail saleDetail){
        saleDetailService.createSaleDetail(saleDetail);
    }

}
