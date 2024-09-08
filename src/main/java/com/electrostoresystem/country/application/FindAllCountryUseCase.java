package com.electrostoresystem.country.application;

import java.util.List;

import com.electrostoresystem.country.domain.entity.Country;
import com.electrostoresystem.country.domain.service.CountryService;

public class FindAllCountryUseCase {
    private final CountryService countryService;

    public FindAllCountryUseCase(CountryService countryService) {
        this.countryService = countryService;
    }

    public List<Country> execute() {
        return countryService.findAllCountry();
    }
}
