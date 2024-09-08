package com.electrostoresystem.region.application;

import com.electrostoresystem.region.domain.entity.Region;
import com.electrostoresystem.region.domain.service.RegionService;

import java.util.List;

public class FindRegionsByCountryUseCase {
    private final RegionService regionService;

    public FindRegionsByCountryUseCase(RegionService regionService) {
        this.regionService = regionService;
    }

    public List<Region> execute(int countryId) {
        return regionService.findRegionsByCountry(countryId);
    }
}
