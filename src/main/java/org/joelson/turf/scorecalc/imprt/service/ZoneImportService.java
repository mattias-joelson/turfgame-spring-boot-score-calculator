package org.joelson.turf.scorecalc.imprt.service;

import org.joelson.turf.scorecalc.imprt.model.RegionImport;
import org.joelson.turf.scorecalc.imprt.model.ZoneImport;
import org.joelson.turf.scorecalc.imprt.model.ZoneImportRepository;
import org.joelson.turf.turfgame.apiv5.Region;
import org.joelson.turf.turfgame.apiv5.Zone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZoneImportService {

    @Autowired
    RegionImportService regionImportService;

    @Autowired
    ZoneImportRepository zoneImportRepository;

    public ZoneImport getOrCreate(Zone turfZone) {
        ZoneImport zone = zoneImportRepository.findById(turfZone.getId()).orElse(null);
        if (zone == null) {
            RegionImport region = regionImportService.getOrCreate(turfZone.getRegion());
            return zoneImportRepository.save(new ZoneImport(turfZone.getId(), region));
        } else {
            Region turfRegion = turfZone.getRegion();
            if (turfRegion == null || zone.getRegion().getRegionId() != turfRegion.getId()) {
                throw new IllegalArgumentException("Different regions.");
            }
        }
        return zone;
    }
}
