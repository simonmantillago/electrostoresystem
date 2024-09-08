package com.electrostoresystem.clienttype.application;

import com.electrostoresystem.clienttype.domain.entity.ClientType;
import com.electrostoresystem.clienttype.domain.service.ClientTypeService;

public class CreateClientTypeUseCase {
    private final ClientTypeService clientTypeService;

    public CreateClientTypeUseCase(ClientTypeService clientTypeService) {
        this.clientTypeService = clientTypeService;
    }

    public void execute(ClientType clientType){
        clientTypeService.createClientType(clientType);
    }

}
