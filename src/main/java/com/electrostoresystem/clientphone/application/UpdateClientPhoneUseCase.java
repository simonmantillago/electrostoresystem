package com.electrostoresystem.clientphone.application;

import com.electrostoresystem.clientphone.domain.entity.ClientPhone;
import com.electrostoresystem.clientphone.domain.service.ClientPhoneService;

public class UpdateClientPhoneUseCase {
    private final ClientPhoneService clientPhoneService;

    public UpdateClientPhoneUseCase(ClientPhoneService clientPhoneService) {
        this.clientPhoneService = clientPhoneService;
    }

    public void execute(ClientPhone clientPhone, String phone){
        clientPhoneService.updateClientPhone(clientPhone,phone);
    }
}
