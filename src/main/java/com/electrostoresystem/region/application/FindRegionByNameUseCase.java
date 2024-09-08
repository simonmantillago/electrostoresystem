package com.electrostoresystem.region.application;

import java.util.Optional;

import com.electrostoresystem.region.domain.entity.Region;
import com.electrostoresystem.region.domain.service.RegionService;

public class FindRegionByNameUseCase {
    private final RegionService regionService;

    public FindRegionByNameUseCase(RegionService regionService) {
        this.regionService = regionService;
    }

    public Optional<Region> execute(String name) {
        return regionService.findRegionByName(name);
    }
}
