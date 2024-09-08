package com.electrostoresystem.country.application;

import com.electrostoresystem.country.domain.entity.Country;
import com.electrostoresystem.country.domain.service.CountryService;

public class UpdateCountryUseCase {
 private final CountryService countryService;

    public UpdateCountryUseCase(CountryService countryService) {
        this.countryService = countryService;
    }

    public void execute(Country country){
        countryService.updateCountry(country);
    }
}
