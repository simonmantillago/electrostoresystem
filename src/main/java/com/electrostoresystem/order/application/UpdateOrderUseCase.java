package com.electrostoresystem.order.application;

import com.electrostoresystem.order.domain.entity.Order;
import com.electrostoresystem.order.domain.service.OrderService;

public class UpdateOrderUseCase {
 private final OrderService orderService;

    public UpdateOrderUseCase(OrderService orderService) {
        this.orderService = orderService;
    }

    public void execute(Order order){
        orderService.updateOrder(order);
    }
}
