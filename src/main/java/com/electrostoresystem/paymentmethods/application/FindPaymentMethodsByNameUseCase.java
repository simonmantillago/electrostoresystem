package com.electrostoresystem.paymentmethods.application;

import java.util.Optional;

import com.electrostoresystem.paymentmethods.domain.entity.PaymentMethods;
import com.electrostoresystem.paymentmethods.domain.service.PaymentMethodsService;

public class FindPaymentMethodsByNameUseCase {
    private final PaymentMethodsService paymentMethodsService;

    public FindPaymentMethodsByNameUseCase(PaymentMethodsService paymentMethodsService) {
        this.paymentMethodsService = paymentMethodsService;
    }

    public Optional<PaymentMethods> execute(String name) {
        return paymentMethodsService.findPaymentMethodsByName(name);
    }
}
