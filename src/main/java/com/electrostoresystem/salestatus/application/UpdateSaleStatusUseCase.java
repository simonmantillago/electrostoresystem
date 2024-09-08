package com.electrostoresystem.salestatus.application;

import com.electrostoresystem.salestatus.domain.entity.SaleStatus;
import com.electrostoresystem.salestatus.domain.service.SaleStatusService;

public class UpdateSaleStatusUseCase {
 private final SaleStatusService saleStatusService;

    public UpdateSaleStatusUseCase(SaleStatusService saleStatusService) {
        this.saleStatusService = saleStatusService;
    }

    public void execute(SaleStatus saleStatus){
        saleStatusService.updateSaleStatus(saleStatus);
    }
}
