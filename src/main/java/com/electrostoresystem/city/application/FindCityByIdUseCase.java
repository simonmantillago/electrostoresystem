package com.electrostoresystem.city.application;

import java.util.Optional;

import com.electrostoresystem.city.domain.entity.City;
import com.electrostoresystem.city.domain.service.CityService;

public class FindCityByIdUseCase {
    private final CityService cityService;

    public FindCityByIdUseCase(CityService cityService) {
        this.cityService = cityService;
    }

    public Optional<City> execute(int id) {
        return cityService.findCityById(id);
    }
}
