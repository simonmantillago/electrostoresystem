package com.electrostoresystem.paymentmethods.application;

import com.electrostoresystem.paymentmethods.domain.entity.PaymentMethods;
import com.electrostoresystem.paymentmethods.domain.service.PaymentMethodsService;

public class CreatePaymentMethodsUseCase {
    private final PaymentMethodsService paymentMethodsService;

    public CreatePaymentMethodsUseCase(PaymentMethodsService paymentMethodsService) {
        this.paymentMethodsService = paymentMethodsService;
    }

    public void execute(PaymentMethods paymentMethods){
        paymentMethodsService.createPaymentMethods(paymentMethods);
    }

}
