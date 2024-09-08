package com.electrostoresystem.city.application;

import com.electrostoresystem.city.domain.entity.City;
import com.electrostoresystem.city.domain.service.CityService;

import java.util.List;

public class FindCitysByRegionUseCase {
    private final CityService cityService;

    public FindCitysByRegionUseCase(CityService cityService) {
        this.cityService = cityService;
    }

    public List<City> execute(int countryId) {
        return cityService.findCitysByRegion(countryId);
    }
}
