package com.electrostoresystem.region.application;

import com.electrostoresystem.region.domain.entity.Region;
import com.electrostoresystem.region.domain.service.RegionService;

public class DeleteRegionUseCase {
    private final RegionService regionService;

    public DeleteRegionUseCase(RegionService regionService) {
        this.regionService = regionService;
    }

    public Region execute(int codeRegion) {
        return regionService.deleteRegion(codeRegion);
    }
}
