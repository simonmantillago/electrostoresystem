package com.electrostoresystem.salestatus.application;

import java.util.Optional;

import com.electrostoresystem.salestatus.domain.entity.SaleStatus;
import com.electrostoresystem.salestatus.domain.service.SaleStatusService;

public class FindSaleStatusByIdUseCase {
    private final SaleStatusService saleStatusService;

    public FindSaleStatusByIdUseCase(SaleStatusService saleStatusService) {
        this.saleStatusService = saleStatusService;
    }

    public Optional<SaleStatus> execute(int id) {
        return saleStatusService.findSaleStatusById(id);
    }
}
