package com.electrostoresystem.country.application;

import java.util.Optional;

import com.electrostoresystem.country.domain.entity.Country;
import com.electrostoresystem.country.domain.service.CountryService;

public class FindCountryByNameUseCase {
    private final CountryService countryService;

    public FindCountryByNameUseCase(CountryService countryService) {
        this.countryService = countryService;
    }

    public Optional<Country> execute(String name) {
        return countryService.findCountryByName(name);
    }
}
