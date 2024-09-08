package com.electrostoresystem.orderstatus.application;

import java.util.Optional;

import com.electrostoresystem.orderstatus.domain.entity.OrderStatus;
import com.electrostoresystem.orderstatus.domain.service.OrderStatusService;

public class FindOrderStatusByIdUseCase {
    private final OrderStatusService orderStatusService;

    public FindOrderStatusByIdUseCase(OrderStatusService orderStatusService) {
        this.orderStatusService = orderStatusService;
    }

    public Optional<OrderStatus> execute(int id) {
        return orderStatusService.findOrderStatusById(id);
    }
}
