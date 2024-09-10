package com.electrostoresystem.sale.application;

import com.electrostoresystem.sale.domain.entity.Sale;
import com.electrostoresystem.sale.domain.service.SaleService;

public class CreateSaleUseCase {
    private final SaleService saleService;

    public CreateSaleUseCase(SaleService saleService) {
        this.saleService = saleService;
    }

    public void execute(Sale sale){
        saleService.createSale(sale);
    }

}
