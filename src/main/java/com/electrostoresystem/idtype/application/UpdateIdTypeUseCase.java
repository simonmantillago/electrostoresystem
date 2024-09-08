package com.electrostoresystem.idtype.application;

import com.electrostoresystem.idtype.domain.entity.IdType;
import com.electrostoresystem.idtype.domain.service.IdTypeService;

public class UpdateIdTypeUseCase {
 private final IdTypeService idTypeService;

    public UpdateIdTypeUseCase(IdTypeService idTypeService) {
        this.idTypeService = idTypeService;
    }

    public void execute(IdType idType){
        idTypeService.updateIdType(idType);
    }
}
