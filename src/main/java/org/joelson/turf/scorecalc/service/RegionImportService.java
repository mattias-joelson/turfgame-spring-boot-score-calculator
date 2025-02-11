package org.joelson.turf.scorecalc.service;

import org.joelson.turf.scorecalc.modelimport.CountryImport;
import org.joelson.turf.scorecalc.modelimport.RegionImport;
import org.joelson.turf.scorecalc.modelimport.RegionImportRepository;
import org.joelson.turf.turfgame.apiv5.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class RegionImportService {

    @Autowired
    private CountryImportService countryImportService;

    @Autowired
    private RegionImportRepository regionImportRepository;

    public RegionImport getOrCreate(Region turfRegion) {
        RegionImport region = regionImportRepository.findById((long) turfRegion.getId()).orElse(null);
        if (region == null) {
            CountryImport country = countryImportService.getOrCreate(turfRegion.getCountry());
            return regionImportRepository.save(new RegionImport((long) turfRegion.getId(), country));
        } else {
            CountryImport country = region.getCountry();
            if ((country == null && turfRegion.getCountry() != null)
                    || (country != null && !Objects.equals(country.getCountry(), turfRegion.getCountry()))) {
                throw new IllegalArgumentException("Different countries.");
            }
        }
        return region;
    }
}
