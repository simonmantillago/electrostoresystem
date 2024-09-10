package com.electrostoresystem.sale.domain.service;

import com.electrostoresystem.sale.domain.entity.Sale;
 
import java.util.List;
import java.util.Optional;

public interface SaleService {
    void createSale (Sale sale);
    void updateSale (Sale sale);
    Sale deleteSale (int id);
    Optional<Sale> findSaleById(int id);
    List<Sale> findAllSale();
}
