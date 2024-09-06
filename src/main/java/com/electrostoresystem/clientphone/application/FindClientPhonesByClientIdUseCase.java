package com.electrostoresystem.clientphone.application;

import java.util.List;

import com.electrostoresystem.clientphone.domain.entity.ClientPhone;
import com.electrostoresystem.clientphone.domain.service.ClientPhoneService;

public class FindClientPhonesByClientIdUseCase {
    private final ClientPhoneService clientPhoneService;

    public FindClientPhonesByClientIdUseCase(ClientPhoneService clientPhoneService) {
        this.clientPhoneService = clientPhoneService;
    }

    public List<ClientPhone> execute(String clientId) {
        return clientPhoneService.findClientPhonesByClientId(clientId);
    }
}
