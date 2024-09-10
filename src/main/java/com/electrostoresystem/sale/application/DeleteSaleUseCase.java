package com.electrostoresystem.sale.application;

import com.electrostoresystem.sale.domain.entity.Sale;
import com.electrostoresystem.sale.domain.service.SaleService;

public class DeleteSaleUseCase {
    private final SaleService saleService;

    public DeleteSaleUseCase(SaleService saleService) {
        this.saleService = saleService;
    }

    public Sale execute(int codeSale) {
        return saleService.deleteSale(codeSale);
    }
}
