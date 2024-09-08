package com.electrostoresystem.idtype.application;

import java.util.Optional;

import com.electrostoresystem.idtype.domain.entity.IdType;
import com.electrostoresystem.idtype.domain.service.IdTypeService;

public class FindIdTypeByIdUseCase {
    private final IdTypeService idTypeService;

    public FindIdTypeByIdUseCase(IdTypeService idTypeService) {
        this.idTypeService = idTypeService;
    }

    public Optional<IdType> execute(int id) {
        return idTypeService.findIdTypeById(id);
    }
}
