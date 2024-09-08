package com.electrostoresystem.region.domain.service;

import java.util.Optional;
import java.util.List;

import com.electrostoresystem.region.domain.entity.Region;

public interface RegionService {
    void createRegion (Region region);
    void updateRegion (Region region);
    Region deleteRegion (int id);
    Optional<Region> findRegionById(int id);
    Optional<Region> findRegionByName(String name);
    List<Region> findRegionsByCountry(int countryId);
    List<Region> findAllRegion();

}
