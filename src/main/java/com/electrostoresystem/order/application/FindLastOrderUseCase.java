package com.electrostoresystem.order.application;

import com.electrostoresystem.order.domain.entity.Order;
import com.electrostoresystem.order.domain.service.OrderService;

public class FindLastOrderUseCase {
    private final OrderService orderService;

    public FindLastOrderUseCase(OrderService orderService) {
        this.orderService = orderService;
    }

    public Order execute() {
        return orderService.findLastOrder();
    }
}
