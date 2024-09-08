package com.electrostoresystem.paymentmethods.application;

import java.util.Optional;

import com.electrostoresystem.paymentmethods.domain.entity.PaymentMethods;
import com.electrostoresystem.paymentmethods.domain.service.PaymentMethodsService;

public class FindPaymentMethodsByIdUseCase {
    private final PaymentMethodsService paymentMethodsService;

    public FindPaymentMethodsByIdUseCase(PaymentMethodsService paymentMethodsService) {
        this.paymentMethodsService = paymentMethodsService;
    }

    public Optional<PaymentMethods> execute(int id) {
        return paymentMethodsService.findPaymentMethodsById(id);
    }
}
