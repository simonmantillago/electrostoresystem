package com.electrostoresystem.client.application;

import com.electrostoresystem.client.domain.entity.Client;
import com.electrostoresystem.client.domain.service.ClientService;

public class CreateClientUseCase {
    private final ClientService clientService;

    public CreateClientUseCase(ClientService clientService) {
        this.clientService = clientService;
    }

    public void execute(Client client){
        clientService.createClient(client);
    }

}
