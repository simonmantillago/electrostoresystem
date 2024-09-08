package com.electrostoresystem.idtype.application;

import com.electrostoresystem.idtype.domain.entity.IdType;
import com.electrostoresystem.idtype.domain.service.IdTypeService;

public class CreateIdTypeUseCase {
    private final IdTypeService idTypeService;

    public CreateIdTypeUseCase(IdTypeService idTypeService) {
        this.idTypeService = idTypeService;
    }

    public void execute(IdType idType){
        idTypeService.createIdType(idType);
    }

}