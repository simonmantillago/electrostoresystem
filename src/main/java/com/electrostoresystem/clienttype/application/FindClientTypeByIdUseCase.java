package com.electrostoresystem.clienttype.application;

import java.util.Optional;

import com.electrostoresystem.clienttype.domain.entity.ClientType;
import com.electrostoresystem.clienttype.domain.service.ClientTypeService;

public class FindClientTypeByIdUseCase {
    private final ClientTypeService clientTypeService;

    public FindClientTypeByIdUseCase(ClientTypeService clientTypeService) {
        this.clientTypeService = clientTypeService;
    }

    public Optional<ClientType> execute(int id) {
        return clientTypeService.findClientTypeById(id);
    }
}
