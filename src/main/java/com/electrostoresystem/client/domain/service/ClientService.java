package com.electrostoresystem.client.domain.service;

import java.util.List;
import java.util.Optional;

import com.electrostoresystem.client.domain.entity.Client;

public interface ClientService {
    void createClient (Client client);
    void updateClient (Client client);
    Client deleteClient (String id);
    Optional<Client> findClientById(String id);
    List<Client> findAllClient();
}
