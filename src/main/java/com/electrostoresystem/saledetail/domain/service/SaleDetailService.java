package com.electrostoresystem.saledetail.domain.service;

import com.electrostoresystem.saledetail.domain.entity.SaleDetail;

import java.util.Optional;
import java.util.List;

public interface SaleDetailService {
    void createSaleDetail (SaleDetail saleDetail);
    void updateSaleDetail (SaleDetail saleDetail);
    SaleDetail deleteSaleDetail (int id);
    void deleteSaleDetailsBySaleId(int saleId);
    Optional<SaleDetail> findSaleDetailById(int id);
    List<SaleDetail> findAllSaleDetail();
    List<SaleDetail> findSaleDetailsBySaleId(int saleId);
}
