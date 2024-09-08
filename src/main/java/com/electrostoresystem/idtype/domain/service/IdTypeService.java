package com.electrostoresystem.idtype.domain.service;

import java.util.Optional;
import java.util.List;

import com.electrostoresystem.idtype.domain.entity.IdType;

public interface IdTypeService {
    void createIdType (IdType idType);
    void updateIdType (IdType idType);
    IdType deleteIdType (int id);
    Optional<IdType> findIdTypeById(int id);
    Optional<IdType> findIdTypeByName(String name);
    List<IdType> findAllIdType();

}
