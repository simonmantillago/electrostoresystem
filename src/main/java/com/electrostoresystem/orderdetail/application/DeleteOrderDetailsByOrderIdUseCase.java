package com.electrostoresystem.orderdetail.application;

import com.electrostoresystem.orderdetail.domain.service.OrderDetailService;

public class DeleteOrderDetailsByOrderIdUseCase {
    private final OrderDetailService orderDetailService;

    public DeleteOrderDetailsByOrderIdUseCase(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    public void execute(int orderId) {
        orderDetailService.deleteOrderDetailsByOrderId(orderId);
    }
}