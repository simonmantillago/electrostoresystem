package com.electrostoresystem.clientphone.application;

import java.util.List;

import com.electrostoresystem.clientphone.domain.entity.ClientPhone;
import com.electrostoresystem.clientphone.domain.service.ClientPhoneService;


public class FindAllClientPhoneUseCase {
    private final ClientPhoneService clientPhoneService;

    public FindAllClientPhoneUseCase(ClientPhoneService clientPhoneService) {
        this.clientPhoneService = clientPhoneService;
    }

    public List<ClientPhone> execute() {
        return clientPhoneService.findAllClientPhone();
    }
}
