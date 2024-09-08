package com.electrostoresystem.orderstatus.application;

import com.electrostoresystem.orderstatus.domain.entity.OrderStatus;
import com.electrostoresystem.orderstatus.domain.service.OrderStatusService;

public class CreateOrderStatusUseCase {
    private final OrderStatusService orderStatusService;

    public CreateOrderStatusUseCase(OrderStatusService orderStatusService) {
        this.orderStatusService = orderStatusService;
    }

    public void execute(OrderStatus orderStatus){
        orderStatusService.createOrderStatus(orderStatus);
    }

}
