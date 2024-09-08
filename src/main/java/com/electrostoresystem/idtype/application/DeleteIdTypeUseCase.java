package com.electrostoresystem.idtype.application;

import com.electrostoresystem.idtype.domain.entity.IdType;
import com.electrostoresystem.idtype.domain.service.IdTypeService;

public class DeleteIdTypeUseCase {
    private final IdTypeService idTypeService;

    public DeleteIdTypeUseCase(IdTypeService idTypeService) {
        this.idTypeService = idTypeService;
    }

    public IdType execute(int codeIdType) {
        return idTypeService.deleteIdType(codeIdType);
    }
}
