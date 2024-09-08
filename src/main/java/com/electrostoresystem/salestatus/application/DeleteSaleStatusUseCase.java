package com.electrostoresystem.salestatus.application;

import com.electrostoresystem.salestatus.domain.entity.SaleStatus;
import com.electrostoresystem.salestatus.domain.service.SaleStatusService;

public class DeleteSaleStatusUseCase {
    private final SaleStatusService saleStatusService;

    public DeleteSaleStatusUseCase(SaleStatusService saleStatusService) {
        this.saleStatusService = saleStatusService;
    }

    public SaleStatus execute(int codeSaleStatus) {
        return saleStatusService.deleteSaleStatus(codeSaleStatus);
    }
}
