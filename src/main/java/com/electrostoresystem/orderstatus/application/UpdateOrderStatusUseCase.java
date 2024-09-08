package com.electrostoresystem.orderstatus.application;

import com.electrostoresystem.orderstatus.domain.entity.OrderStatus;
import com.electrostoresystem.orderstatus.domain.service.OrderStatusService;

public class UpdateOrderStatusUseCase {
 private final OrderStatusService orderStatusService;

    public UpdateOrderStatusUseCase(OrderStatusService orderStatusService) {
        this.orderStatusService = orderStatusService;
    }

    public void execute(OrderStatus orderStatus){
        orderStatusService.updateOrderStatus(orderStatus);
    }
}
