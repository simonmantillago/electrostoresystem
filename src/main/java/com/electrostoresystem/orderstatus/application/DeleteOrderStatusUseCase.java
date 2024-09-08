package com.electrostoresystem.orderstatus.application;

import com.electrostoresystem.orderstatus.domain.entity.OrderStatus;
import com.electrostoresystem.orderstatus.domain.service.OrderStatusService;

public class DeleteOrderStatusUseCase {
    private final OrderStatusService orderStatusService;

    public DeleteOrderStatusUseCase(OrderStatusService orderStatusService) {
        this.orderStatusService = orderStatusService;
    }

    public OrderStatus execute(int codeOrderStatus) {
        return orderStatusService.deleteOrderStatus(codeOrderStatus);
    }
}
