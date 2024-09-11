package com.electrostoresystem.orderdetail.application;

import java.util.List;

import com.electrostoresystem.orderdetail.domain.entity.OrderDetail;
import com.electrostoresystem.orderdetail.domain.service.OrderDetailService;

public class FindAllOrderDetailUseCase {
    private final OrderDetailService orderDetailService;

    public FindAllOrderDetailUseCase(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    public List<OrderDetail> execute() {
        return orderDetailService.findAllOrderDetail();
    }
}
