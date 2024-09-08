package com.electrostoresystem.country.domain.service;

import java.util.Optional;
import java.util.List;

import com.electrostoresystem.country.domain.entity.Country;

public interface CountryService {
    void createCountry (Country country);
    void updateCountry (Country country);
    Country deleteCountry (int id);
    Optional<Country> findCountryById(int id);
    Optional<Country> findCountryByName(String name);
    List<Country> findAllCountry();

}
