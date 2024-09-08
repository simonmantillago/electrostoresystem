package com.electrostoresystem.paymentmethods.application;

import com.electrostoresystem.paymentmethods.domain.entity.PaymentMethods;
import com.electrostoresystem.paymentmethods.domain.service.PaymentMethodsService;

public class UpdatePaymentMethodsUseCase {
 private final PaymentMethodsService paymentMethodsService;

    public UpdatePaymentMethodsUseCase(PaymentMethodsService paymentMethodsService) {
        this.paymentMethodsService = paymentMethodsService;
    }

    public void execute(PaymentMethods paymentMethods){
        paymentMethodsService.updatePaymentMethods(paymentMethods);
    }
}
