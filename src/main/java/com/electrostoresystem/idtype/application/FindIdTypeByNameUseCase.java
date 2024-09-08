package com.electrostoresystem.idtype.application;

import java.util.Optional;

import com.electrostoresystem.idtype.domain.entity.IdType;
import com.electrostoresystem.idtype.domain.service.IdTypeService;

public class FindIdTypeByNameUseCase {
    private final IdTypeService idTypeService;

    public FindIdTypeByNameUseCase(IdTypeService idTypeService) {
        this.idTypeService = idTypeService;
    }

    public Optional<IdType> execute(String name) {
        return idTypeService.findIdTypeByName(name);
    }
}
