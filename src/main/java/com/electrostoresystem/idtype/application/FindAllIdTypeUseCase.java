package com.electrostoresystem.idtype.application;

import java.util.List;

import com.electrostoresystem.idtype.domain.entity.IdType;
import com.electrostoresystem.idtype.domain.service.IdTypeService;

public class FindAllIdTypeUseCase {
    private final IdTypeService idTypeService;

    public FindAllIdTypeUseCase(IdTypeService idTypeService) {
        this.idTypeService = idTypeService;
    }

    public List<IdType> execute() {
        return idTypeService.findAllIdType();
    }
}
