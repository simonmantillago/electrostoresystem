package com.electrostoresystem.salestatus.application;

import java.util.Optional;

import com.electrostoresystem.salestatus.domain.entity.SaleStatus;
import com.electrostoresystem.salestatus.domain.service.SaleStatusService;

public class FindSaleStatusByNameUseCase {
    private final SaleStatusService saleStatusService;

    public FindSaleStatusByNameUseCase(SaleStatusService saleStatusService) {
        this.saleStatusService = saleStatusService;
    }

    public Optional<SaleStatus> execute(String name) {
        return saleStatusService.findSaleStatusByName(name);
    }
}
