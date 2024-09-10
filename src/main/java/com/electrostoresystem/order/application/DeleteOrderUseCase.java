package com.electrostoresystem.order.application;

import com.electrostoresystem.order.domain.entity.Order;
import com.electrostoresystem.order.domain.service.OrderService;

public class DeleteOrderUseCase {
    private final OrderService orderService;

    public DeleteOrderUseCase(OrderService orderService) {
        this.orderService = orderService;
    }

    public Order execute(int codeOrder) {
        return orderService.deleteOrder(codeOrder);
    }
}
