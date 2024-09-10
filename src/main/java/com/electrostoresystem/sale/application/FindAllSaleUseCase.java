package com.electrostoresystem.sale.application;

import java.util.List;

import com.electrostoresystem.sale.domain.entity.Sale;
import com.electrostoresystem.sale.domain.service.SaleService;

public class FindAllSaleUseCase {
    private final SaleService saleService;

    public FindAllSaleUseCase(SaleService saleService) {
        this.saleService = saleService;
    }

    public List<Sale> execute() {
        return saleService.findAllSale();
    }
}
