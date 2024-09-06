package com.electrostoresystem.clientphone.domain.service;

import java.util.List;
import java.util.Optional;




import com.electrostoresystem.clientphone.domain.entity.ClientPhone;

public interface ClientPhoneService {
    void createClientPhone (ClientPhone clientPhone);
    void updateClientPhone (ClientPhone clientPhone, String originalPhone);
    ClientPhone deleteClientPhone (String Phone);
    Optional<ClientPhone> findClientPhoneByPhone(String Phone);
    List<ClientPhone> findAllClientPhone();
    List<ClientPhone> findClientPhonesByClientId(String clientId);
}
