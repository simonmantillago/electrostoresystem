package com.electrostoresystem.orderdetail.application;

import java.util.List;

import com.electrostoresystem.orderdetail.domain.entity.OrderDetail;
import com.electrostoresystem.orderdetail.domain.service.OrderDetailService;

public class FindOrderDetailsByOrderIdUseCase {
    private final OrderDetailService orderDetailService;

    public FindOrderDetailsByOrderIdUseCase(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    public List<OrderDetail> execute(int orderId) {
        return orderDetailService.findOrderDetailsByOrderId(orderId);
    }
}
