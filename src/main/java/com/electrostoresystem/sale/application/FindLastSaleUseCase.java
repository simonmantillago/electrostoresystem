package com.electrostoresystem.sale.application;

import com.electrostoresystem.sale.domain.entity.Sale;
import com.electrostoresystem.sale.domain.service.SaleService;

public class FindLastSaleUseCase {
    private final SaleService saleService;

    public FindLastSaleUseCase(SaleService saleService) {
        this.saleService = saleService;
    }

    public Sale execute() {
        return saleService.findLastSale();
    }
}
