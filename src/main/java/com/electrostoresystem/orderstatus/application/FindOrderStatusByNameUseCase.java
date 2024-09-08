package com.electrostoresystem.orderstatus.application;

import java.util.Optional;

import com.electrostoresystem.orderstatus.domain.entity.OrderStatus;
import com.electrostoresystem.orderstatus.domain.service.OrderStatusService;

public class FindOrderStatusByNameUseCase {
    private final OrderStatusService orderStatusService;

    public FindOrderStatusByNameUseCase(OrderStatusService orderStatusService) {
        this.orderStatusService = orderStatusService;
    }

    public Optional<OrderStatus> execute(String name) {
        return orderStatusService.findOrderStatusByName(name);
    }
}
