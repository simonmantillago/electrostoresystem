package com.electrostoresystem.client.application;

import com.electrostoresystem.client.domain.entity.Client;
import com.electrostoresystem.client.domain.service.ClientService;

public class UpdateClientUseCase {
 private final ClientService clientService;

    public UpdateClientUseCase(ClientService clientService) {
        this.clientService = clientService;
    }

    public void execute(Client client){
        clientService.updateClient(client);
    }
}
