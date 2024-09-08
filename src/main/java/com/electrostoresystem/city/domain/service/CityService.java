package com.electrostoresystem.city.domain.service;

import java.util.Optional;
import java.util.List;

import com.electrostoresystem.city.domain.entity.City;

public interface CityService {
    void createCity (City city);
    void updateCity (City city);
    City deleteCity (int id);
    Optional<City> findCityById(int id);
    Optional<City> findCityByName(String name);
    List<City> findCitysByRegion(int cityId);
    List<City> findAllCity();

}
