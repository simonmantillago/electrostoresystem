package com.electrostoresystem.clienttype.application;

import com.electrostoresystem.clienttype.domain.entity.ClientType;
import com.electrostoresystem.clienttype.domain.service.ClientTypeService;

public class UpdateClientTypeUseCase {
 private final ClientTypeService clientTypeService;

    public UpdateClientTypeUseCase(ClientTypeService clientTypeService) {
        this.clientTypeService = clientTypeService;
    }

    public void execute(ClientType clientType){
        clientTypeService.updateClientType(clientType);
    }
}
