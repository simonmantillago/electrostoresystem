package com.electrostoresystem.saledetail.application;

import com.electrostoresystem.saledetail.domain.entity.SaleDetail;
import com.electrostoresystem.saledetail.domain.service.SaleDetailService;

public class UpdateSaleDetailUseCase {
 private final SaleDetailService saleDetailService;

    public UpdateSaleDetailUseCase(SaleDetailService saleDetailService) {
        this.saleDetailService = saleDetailService;
    }

    public void execute(SaleDetail saleDetail){
        saleDetailService.updateSaleDetail(saleDetail);
    }
}
