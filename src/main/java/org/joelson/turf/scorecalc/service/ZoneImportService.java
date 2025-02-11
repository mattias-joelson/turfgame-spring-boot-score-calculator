package org.joelson.turf.scorecalc.service;

import org.joelson.turf.scorecalc.model.TurfgameAssertionException;
import org.joelson.turf.scorecalc.model.Zone;
import org.joelson.turf.scorecalc.model.ZoneRepository;
import org.joelson.turf.turfgame.apiv5.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ZoneImportService {

    @Autowired
    ZoneRepository zoneRepository;

    public Zone getOrCreate(org.joelson.turf.turfgame.apiv5.Zone turfZone) {
        Zone zone = zoneRepository.findById(turfZone.getId()).orElse(null);
        Region turfRegion = turfZone.getRegion();
        if (zone == null) {
            return zoneRepository.save(new Zone(turfZone.getId(), turfRegion.getId(), turfRegion.getCountry()));
        } else {
            if (!Objects.equals(zone.getRegionId(), turfRegion.getId())) {
                throw new TurfgameAssertionException(String.format("%s differs from Turf regionId %d",
                        zone, turfRegion.getId()));
            }
            if (!Objects.equals(zone.getCountry(), turfRegion.getCountry())) {
                throw new TurfgameAssertionException(String.format("%s differs from Turf country %s",
                        zone, turfRegion.getCountry()));
            }
        }
        return zone;
    }
}
