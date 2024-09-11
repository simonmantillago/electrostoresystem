package com.electrostoresystem.order.domain.service;

import com.electrostoresystem.order.domain.entity.Order;
import com.electrostoresystem.sale.domain.entity.Sale;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    void createOrder (Order order);
    void updateOrder (Order order);
    Order deleteOrder (int id);
    Optional<Order> findOrderById(int id);
    List<Order> findAllOrder();
    Order findLastOrder();
}
