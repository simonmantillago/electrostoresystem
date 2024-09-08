package com.electrostoresystem.clienttype.application;

import com.electrostoresystem.clienttype.domain.entity.ClientType;
import com.electrostoresystem.clienttype.domain.service.ClientTypeService;

public class DeleteClientTypeUseCase {
    private final ClientTypeService clientTypeService;

    public DeleteClientTypeUseCase(ClientTypeService clientTypeService) {
        this.clientTypeService = clientTypeService;
    }

    public ClientType execute(int codeClientType) {
        return clientTypeService.deleteClientType(codeClientType);
    }
}
