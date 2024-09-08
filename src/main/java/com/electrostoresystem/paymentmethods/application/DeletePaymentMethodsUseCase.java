package com.electrostoresystem.paymentmethods.application;

import com.electrostoresystem.paymentmethods.domain.entity.PaymentMethods;
import com.electrostoresystem.paymentmethods.domain.service.PaymentMethodsService;

public class DeletePaymentMethodsUseCase {
    private final PaymentMethodsService paymentMethodsService;

    public DeletePaymentMethodsUseCase(PaymentMethodsService paymentMethodsService) {
        this.paymentMethodsService = paymentMethodsService;
    }

    public PaymentMethods execute(int codePaymentMethods) {
        return paymentMethodsService.deletePaymentMethods(codePaymentMethods);
    }
}
