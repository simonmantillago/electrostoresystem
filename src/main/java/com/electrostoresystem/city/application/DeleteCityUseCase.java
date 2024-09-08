package com.electrostoresystem.city.application;

import com.electrostoresystem.city.domain.entity.City;
import com.electrostoresystem.city.domain.service.CityService;

public class DeleteCityUseCase {
    private final CityService cityService;

    public DeleteCityUseCase(CityService cityService) {
        this.cityService = cityService;
    }

    public City execute(int codeCity) {
        return cityService.deleteCity(codeCity);
    }
}
