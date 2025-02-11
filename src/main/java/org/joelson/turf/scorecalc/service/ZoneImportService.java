package org.joelson.turf.scorecalc.service;

import org.joelson.turf.scorecalc.modelimport.RegionImport;
import org.joelson.turf.scorecalc.modelimport.ZoneImport;
import org.joelson.turf.scorecalc.modelimport.ZoneImportRepository;
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
        ZoneImport zone = zoneImportRepository.findById((long) turfZone.getId()).orElse(null);
        if (zone == null) {
            RegionImport region = regionImportService.getOrCreate(turfZone.getRegion());
            return zoneImportRepository.save(new ZoneImport((long) turfZone.getId(), region));
        } else {
            Region turfRegion = turfZone.getRegion();
            if (turfRegion == null || zone.getRegion().getRegionId() != turfRegion.getId()) {
                throw new IllegalArgumentException("Different regions.");
            }
        }
        return zone;
    }
}
