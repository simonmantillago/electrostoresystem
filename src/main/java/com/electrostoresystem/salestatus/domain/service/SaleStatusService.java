package com.electrostoresystem.salestatus.domain.service;

import java.util.Optional;
import java.util.List;

import com.electrostoresystem.salestatus.domain.entity.SaleStatus;

public interface SaleStatusService {
    void createSaleStatus (SaleStatus saleStatus);
    void updateSaleStatus (SaleStatus saleStatus);
    SaleStatus deleteSaleStatus (int id);
    Optional<SaleStatus> findSaleStatusById(int id);
    Optional<SaleStatus> findSaleStatusByName(String name);
    List<SaleStatus> findAllSaleStatus();

}
