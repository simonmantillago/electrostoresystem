package com.electrostoresystem.order.application;

import com.electrostoresystem.order.domain.entity.Order;
import com.electrostoresystem.order.domain.service.OrderService;

public class CreateOrderUseCase {
    private final OrderService orderService;

    public CreateOrderUseCase(OrderService orderService) {
        this.orderService = orderService;
    }

    public void execute(Order order){
        orderService.createOrder(order);
    }

}
