package com.electrostoresystem.clienttype.application;

import java.util.List;

import com.electrostoresystem.clienttype.domain.entity.ClientType;
import com.electrostoresystem.clienttype.domain.service.ClientTypeService;

public class FindAllClientTypeUseCase {
    private final ClientTypeService clientTypeService;

    public FindAllClientTypeUseCase(ClientTypeService clientTypeService) {
        this.clientTypeService = clientTypeService;
    }

    public List<ClientType> execute() {
        return clientTypeService.findAllClientType();
    }
}
