package com.electrostoresystem.order.application;

import java.util.List;

import com.electrostoresystem.order.domain.entity.Order;
import com.electrostoresystem.order.domain.service.OrderService;

public class FindAllOrderUseCase {
    private final OrderService orderService;

    public FindAllOrderUseCase(OrderService orderService) {
        this.orderService = orderService;
    }

    public List<Order> execute() {
        return orderService.findAllOrder();
    }
}
