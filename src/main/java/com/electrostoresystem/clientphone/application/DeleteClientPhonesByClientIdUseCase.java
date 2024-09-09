package com.electrostoresystem.clientphone.application;

import com.electrostoresystem.clientphone.domain.service.ClientPhoneService;

public class DeleteClientPhonesByClientIdUseCase {
    private final ClientPhoneService clientPhoneService;

    public DeleteClientPhonesByClientIdUseCase(ClientPhoneService clientPhoneService) {
        this.clientPhoneService = clientPhoneService;
    }

    public void execute(String clientId) {
        clientPhoneService.deleteClientPhonesByClientId(clientId);
    }
}