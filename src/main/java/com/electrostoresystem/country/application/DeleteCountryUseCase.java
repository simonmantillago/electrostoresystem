package com.electrostoresystem.country.application;

import com.electrostoresystem.country.domain.entity.Country;
import com.electrostoresystem.country.domain.service.CountryService;

public class DeleteCountryUseCase {
    private final CountryService countryService;

    public DeleteCountryUseCase(CountryService countryService) {
        this.countryService = countryService;
    }

    public Country execute(int codeCountry) {
        return countryService.deleteCountry(codeCountry);
    }
}
