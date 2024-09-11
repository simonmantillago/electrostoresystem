package com.electrostoresystem.orderdetail.application;

import com.electrostoresystem.orderdetail.domain.entity.OrderDetail;
import com.electrostoresystem.orderdetail.domain.service.OrderDetailService;

public class UpdateOrderDetailUseCase {
 private final OrderDetailService orderDetailService;

    public UpdateOrderDetailUseCase(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    public void execute(OrderDetail orderDetail){
        orderDetailService.updateOrderDetail(orderDetail);
    }
}
