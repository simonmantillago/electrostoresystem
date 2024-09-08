package com.electrostoresystem.orderstatus.domain.service;

import java.util.Optional;
import java.util.List;

import com.electrostoresystem.orderstatus.domain.entity.OrderStatus;

public interface OrderStatusService {
    void createOrderStatus (OrderStatus orderStatus);
    void updateOrderStatus (OrderStatus orderStatus);
    OrderStatus deleteOrderStatus (int id);
    Optional<OrderStatus> findOrderStatusById(int id);
    Optional<OrderStatus> findOrderStatusByName(String name);
    List<OrderStatus> findAllOrderStatus();

}
