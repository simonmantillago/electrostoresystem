package com.electrostoresystem.salestatus.application;

import java.util.List;

import com.electrostoresystem.salestatus.domain.entity.SaleStatus;
import com.electrostoresystem.salestatus.domain.service.SaleStatusService;

public class FindAllSaleStatusUseCase {
    private final SaleStatusService saleStatusService;

    public FindAllSaleStatusUseCase(SaleStatusService saleStatusService) {
        this.saleStatusService = saleStatusService;
    }

    public List<SaleStatus> execute() {
        return saleStatusService.findAllSaleStatus();
    }
}
