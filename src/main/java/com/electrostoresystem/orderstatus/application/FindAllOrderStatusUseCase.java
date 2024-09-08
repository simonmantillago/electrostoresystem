package com.electrostoresystem.orderstatus.application;

import java.util.List;

import com.electrostoresystem.orderstatus.domain.entity.OrderStatus;
import com.electrostoresystem.orderstatus.domain.service.OrderStatusService;

public class FindAllOrderStatusUseCase {
    private final OrderStatusService orderStatusService;

    public FindAllOrderStatusUseCase(OrderStatusService orderStatusService) {
        this.orderStatusService = orderStatusService;
    }

    public List<OrderStatus> execute() {
        return orderStatusService.findAllOrderStatus();
    }
}
