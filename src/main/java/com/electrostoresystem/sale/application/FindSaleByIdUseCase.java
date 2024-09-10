package com.electrostoresystem.sale.application;

import java.util.Optional;

import com.electrostoresystem.sale.domain.entity.Sale;
import com.electrostoresystem.sale.domain.service.SaleService;

public class FindSaleByIdUseCase {
    private final SaleService saleService;

    public FindSaleByIdUseCase(SaleService saleService) {
        this.saleService = saleService;
    }

    public Optional<Sale> execute(int id) {
        return saleService.findSaleById(id);
    }
}
