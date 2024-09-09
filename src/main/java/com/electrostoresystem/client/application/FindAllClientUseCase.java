package com.electrostoresystem.client.application;

import java.util.List;

import com.electrostoresystem.client.domain.entity.Client;
import com.electrostoresystem.client.domain.service.ClientService;

public class FindAllClientUseCase {
    private final ClientService clientService;

    public FindAllClientUseCase(ClientService clientService) {
        this.clientService = clientService;
    }

    public List<Client> execute() {
        return clientService.findAllClient();
    }
}
