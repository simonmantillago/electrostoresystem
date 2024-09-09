package com.electrostoresystem.client.application;

import com.electrostoresystem.client.domain.entity.Client;
import com.electrostoresystem.client.domain.service.ClientService;

public class DeleteClientUseCase {
    private final ClientService clientService;

    public DeleteClientUseCase(ClientService clientService) {
        this.clientService = clientService;
    }

    public Client execute(String codeClient) {
        return clientService.deleteClient(codeClient);
    }
}
