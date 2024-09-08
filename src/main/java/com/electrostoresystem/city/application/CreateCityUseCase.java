package com.electrostoresystem.city.application;

import com.electrostoresystem.city.domain.entity.City;
import com.electrostoresystem.city.domain.service.CityService;

public class CreateCityUseCase {
    private final CityService cityService;

    public CreateCityUseCase(CityService cityService) {
        this.cityService = cityService;
    }

    public void execute(City city){
        cityService.createCity(city);
    }

}
