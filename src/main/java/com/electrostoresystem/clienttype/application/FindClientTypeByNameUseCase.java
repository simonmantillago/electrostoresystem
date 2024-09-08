package com.electrostoresystem.clienttype.application;

import java.util.Optional;

import com.electrostoresystem.clienttype.domain.entity.ClientType;
import com.electrostoresystem.clienttype.domain.service.ClientTypeService;

public class FindClientTypeByNameUseCase {
    private final ClientTypeService clientTypeService;

    public FindClientTypeByNameUseCase(ClientTypeService clientTypeService) {
        this.clientTypeService = clientTypeService;
    }

    public Optional<ClientType> execute(String name) {
        return clientTypeService.findClientTypeByName(name);
    }
}
