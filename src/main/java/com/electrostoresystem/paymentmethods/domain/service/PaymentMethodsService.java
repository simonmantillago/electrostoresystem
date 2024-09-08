package com.electrostoresystem.paymentmethods.domain.service;

import java.util.Optional;
import java.util.List;

import com.electrostoresystem.paymentmethods.domain.entity.PaymentMethods;

public interface PaymentMethodsService {
    void createPaymentMethods (PaymentMethods paymentMethods);
    void updatePaymentMethods (PaymentMethods paymentMethods);
    PaymentMethods deletePaymentMethods (int id);
    Optional<PaymentMethods> findPaymentMethodsById(int id);
    Optional<PaymentMethods> findPaymentMethodsByName(String name);
    List<PaymentMethods> findAllPaymentMethods();

}
