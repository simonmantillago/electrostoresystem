package com.electrostoresystem.clientphone.application;

import java.util.Optional;

import com.electrostoresystem.clientphone.domain.entity.ClientPhone;
import com.electrostoresystem.clientphone.domain.service.ClientPhoneService;

public class FindClientPhoneByPhoneUseCase {
    private final ClientPhoneService clientPhoneService;

    public FindClientPhoneByPhoneUseCase(ClientPhoneService clientPhoneService) {
        this.clientPhoneService = clientPhoneService;
    }

    public Optional<ClientPhone> execute(String phone) {
        return clientPhoneService.findClientPhoneByPhone(phone);
    }
}
