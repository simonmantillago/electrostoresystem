package com.electrostoresystem.city.application;

import java.util.List;

import com.electrostoresystem.city.domain.entity.City;
import com.electrostoresystem.city.domain.service.CityService;

public class FindAllCityUseCase {
    private final CityService cityService;

    public FindAllCityUseCase(CityService cityService) {
        this.cityService = cityService;
    }

    public List<City> execute() {
        return cityService.findAllCity();
    }
}
