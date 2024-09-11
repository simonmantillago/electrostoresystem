package com.electrostoresystem.orderdetail.application;

import com.electrostoresystem.orderdetail.domain.entity.OrderDetail;
import com.electrostoresystem.orderdetail.domain.service.OrderDetailService;

public class CreateOrderDetailUseCase {
    private final OrderDetailService orderDetailService;

    public CreateOrderDetailUseCase(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    public void execute(OrderDetail orderDetail){
        orderDetailService.createOrderDetail(orderDetail);
    }

}
