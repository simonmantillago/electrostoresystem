package com.electrostoresystem.order.application;

import java.util.Optional;

import com.electrostoresystem.order.domain.entity.Order;
import com.electrostoresystem.order.domain.service.OrderService;

public class FindOrderByIdUseCase {
    private final OrderService orderService;

    public FindOrderByIdUseCase(OrderService orderService) {
        this.orderService = orderService;
    }

    public Optional<Order> execute(int id) {
        return orderService.findOrderById(id);
    }
}
