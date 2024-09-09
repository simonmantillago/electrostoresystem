package com.electrostoresystem.client.application;

import java.util.Optional;

import com.electrostoresystem.client.domain.entity.Client;
import com.electrostoresystem.client.domain.service.ClientService;

public class FindClientByIdUseCase {
    private final ClientService clientService;

    public FindClientByIdUseCase(ClientService clientService) {
        this.clientService = clientService;
    }

    public Optional<Client> execute(String id) {
        return clientService.findClientById(id);
    }
}
