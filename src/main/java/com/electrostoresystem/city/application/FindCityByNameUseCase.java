package com.electrostoresystem.city.application;

import java.util.Optional;

import com.electrostoresystem.city.domain.entity.City;
import com.electrostoresystem.city.domain.service.CityService;

public class FindCityByNameUseCase {
    private final CityService cityService;

    public FindCityByNameUseCase(CityService cityService) {
        this.cityService = cityService;
    }

    public Optional<City> execute(String name) {
        return cityService.findCityByName(name);
    }
}
