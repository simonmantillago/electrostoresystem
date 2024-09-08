package com.electrostoresystem.country.application;

import java.util.Optional;

import com.electrostoresystem.country.domain.entity.Country;
import com.electrostoresystem.country.domain.service.CountryService;

public class FindCountryByIdUseCase {
    private final CountryService countryService;

    public FindCountryByIdUseCase(CountryService countryService) {
        this.countryService = countryService;
    }

    public Optional<Country> execute(int id) {
        return countryService.findCountryById(id);
    }
}
