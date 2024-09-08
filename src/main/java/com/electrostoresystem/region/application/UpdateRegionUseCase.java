package com.electrostoresystem.region.application;

import com.electrostoresystem.region.domain.entity.Region;
import com.electrostoresystem.region.domain.service.RegionService;

public class UpdateRegionUseCase {
 private final RegionService regionService;

    public UpdateRegionUseCase(RegionService regionService) {
        this.regionService = regionService;
    }

    public void execute(Region region){
        regionService.updateRegion(region);
    }
}
