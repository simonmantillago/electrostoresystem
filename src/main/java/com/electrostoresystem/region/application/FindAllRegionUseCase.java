package com.electrostoresystem.region.application;

import java.util.List;

import com.electrostoresystem.region.domain.entity.Region;
import com.electrostoresystem.region.domain.service.RegionService;

public class FindAllRegionUseCase {
    private final RegionService regionService;

    public FindAllRegionUseCase(RegionService regionService) {
        this.regionService = regionService;
    }

    public List<Region> execute() {
        return regionService.findAllRegion();
    }
}
