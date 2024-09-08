package com.electrostoresystem.paymentmethods.application;

import java.util.List;

import com.electrostoresystem.paymentmethods.domain.entity.PaymentMethods;
import com.electrostoresystem.paymentmethods.domain.service.PaymentMethodsService;

public class FindAllPaymentMethodsUseCase {
    private final PaymentMethodsService paymentMethodsService;

    public FindAllPaymentMethodsUseCase(PaymentMethodsService paymentMethodsService) {
        this.paymentMethodsService = paymentMethodsService;
    }

    public List<PaymentMethods> execute() {
        return paymentMethodsService.findAllPaymentMethods();
    }
}
