package com.electrostoresystem.clientphone.application;

import com.electrostoresystem.clientphone.domain.entity.ClientPhone;
import com.electrostoresystem.clientphone.domain.service.ClientPhoneService;

public class DeleteClientPhoneUseCase {
    private final ClientPhoneService clientPhoneService;

    public DeleteClientPhoneUseCase(ClientPhoneService clientPhoneService) {
        this.clientPhoneService = clientPhoneService;
    }

    public ClientPhone execute(String Phone) {
        return clientPhoneService.deleteClientPhone(Phone);
    }
}
