package com.electrostoresystem.clientphone.application;

import com.electrostoresystem.clientphone.domain.entity.ClientPhone;
import com.electrostoresystem.clientphone.domain.service.ClientPhoneService;

public class CreateClientPhoneUseCase {
private final ClientPhoneService clientPhoneService;

    public CreateClientPhoneUseCase(ClientPhoneService clientPhoneService) {
        this.clientPhoneService = clientPhoneService;
    }

    public void execute(ClientPhone clientPhone) {
        clientPhoneService.createClientPhone(clientPhone);
    }
}
