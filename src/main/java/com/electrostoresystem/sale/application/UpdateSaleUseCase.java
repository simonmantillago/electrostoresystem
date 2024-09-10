package com.electrostoresystem.sale.application;

import com.electrostoresystem.sale.domain.entity.Sale;
import com.electrostoresystem.sale.domain.service.SaleService;

public class UpdateSaleUseCase {
 private final SaleService saleService;

    public UpdateSaleUseCase(SaleService saleService) {
        this.saleService = saleService;
    }

    public void execute(Sale sale){
        saleService.updateSale(sale);
    }
}
