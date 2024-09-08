package com.electrostoresystem.clienttype.domain.service;

import java.util.Optional;
import java.util.List;

import com.electrostoresystem.clienttype.domain.entity.ClientType;

public interface ClientTypeService {
    void createClientType (ClientType clientType);
    void updateClientType (ClientType clientType);
    ClientType deleteClientType (int id);
    Optional<ClientType> findClientTypeById(int id);
    Optional<ClientType> findClientTypeByName(String name);
    List<ClientType> findAllClientType();

}
