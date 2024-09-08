package com.electrostoresystem.salestatus.application;

import com.electrostoresystem.salestatus.domain.entity.SaleStatus;
import com.electrostoresystem.salestatus.domain.service.SaleStatusService;

public class CreateSaleStatusUseCase {
    private final SaleStatusService saleStatusService;

    public CreateSaleStatusUseCase(SaleStatusService saleStatusService) {
        this.saleStatusService = saleStatusService;
    }

    public void execute(SaleStatus saleStatus){
        saleStatusService.createSaleStatus(saleStatus);
    }

}
