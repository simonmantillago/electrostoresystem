package com.electrostoresystem.city.application;

import com.electrostoresystem.city.domain.entity.City;
import com.electrostoresystem.city.domain.service.CityService;

public class UpdateCityUseCase {
 private final CityService cityService;

    public UpdateCityUseCase(CityService cityService) {
        this.cityService = cityService;
    }

    public void execute(City city){
        cityService.updateCity(city);
    }
}
