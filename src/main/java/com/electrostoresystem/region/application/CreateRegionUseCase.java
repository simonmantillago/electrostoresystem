package com.electrostoresystem.region.application;

import com.electrostoresystem.region.domain.entity.Region;
import com.electrostoresystem.region.domain.service.RegionService;

public class CreateRegionUseCase {
    private final RegionService regionService;

    public CreateRegionUseCase(RegionService regionService) {
        this.regionService = regionService;
    }

    public void execute(Region region){
        regionService.createRegion(region);
    }

}
