package com.electrostoresystem.orderdetail.application;

import java.util.Optional;

import com.electrostoresystem.orderdetail.domain.entity.OrderDetail;
import com.electrostoresystem.orderdetail.domain.service.OrderDetailService;

public class FindOrderDetailByIdUseCase {
    private final OrderDetailService orderDetailService;

    public FindOrderDetailByIdUseCase(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    public Optional<OrderDetail> execute(int id) {
        return orderDetailService.findOrderDetailById(id);
    }
}
