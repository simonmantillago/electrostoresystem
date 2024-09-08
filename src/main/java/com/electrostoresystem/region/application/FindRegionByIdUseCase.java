package com.electrostoresystem.region.application;

import java.util.Optional;

import com.electrostoresystem.region.domain.entity.Region;
import com.electrostoresystem.region.domain.service.RegionService;

public class FindRegionByIdUseCase {
    private final RegionService regionService;

    public FindRegionByIdUseCase(RegionService regionService) {
        this.regionService = regionService;
    }

    public Optional<Region> execute(int id) {
        return regionService.findRegionById(id);
    }
}
