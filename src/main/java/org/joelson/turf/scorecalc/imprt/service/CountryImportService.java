package org.joelson.turf.scorecalc.imprt.service;

import org.joelson.turf.scorecalc.imprt.model.CountryImport;
import org.joelson.turf.scorecalc.imprt.model.CountryImportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryImportService {

    @Autowired
    CountryImportRepository countryImportRepository;

    public CountryImport getOrCreate(String turfCountry) {
        if (turfCountry == null) {
            return null;
        }
        if (turfCountry.isEmpty()) {
            throw new IllegalArgumentException("Empty country name");
        }
        CountryImport country = countryImportRepository.findById(turfCountry).orElse(null);
        if (country == null) {
            return countryImportRepository.save(new CountryImport(turfCountry));
        }
        return country;
    }
}
