package com.electrostoresystem.orderdetail.application;

import com.electrostoresystem.orderdetail.domain.entity.OrderDetail;
import com.electrostoresystem.orderdetail.domain.service.OrderDetailService;

public class DeleteOrderDetailUseCase {
    private final OrderDetailService orderDetailService;

    public DeleteOrderDetailUseCase(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    public OrderDetail execute(int codeOrderDetail) {
        return orderDetailService.deleteOrderDetail(codeOrderDetail);
    }
}
