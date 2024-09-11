package com.electrostoresystem.orderdetail.domain.service;

import com.electrostoresystem.orderdetail.domain.entity.OrderDetail;

import java.util.Optional;
import java.util.List;

public interface OrderDetailService {
    void createOrderDetail (OrderDetail orderDetail);
    void updateOrderDetail (OrderDetail orderDetail);
    OrderDetail deleteOrderDetail (int id);
    void deleteOrderDetailsByOrderId(int orderId);
    Optional<OrderDetail> findOrderDetailById(int id);
    List<OrderDetail> findAllOrderDetail();
    List<OrderDetail> findOrderDetailsByOrderId(int orderId);
}
