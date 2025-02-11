package org.joelson.turf.scorecalc.service;

import org.joelson.turf.scorecalc.modelimport.AssistImport;
import org.joelson.turf.scorecalc.modelimport.AssistImportRepository;
import org.joelson.turf.scorecalc.modelimport.UserImport;
import org.joelson.turf.scorecalc.modelimport.VisitImport;
import org.joelson.turf.scorecalc.modelimport.VisitImportId;
import org.joelson.turf.scorecalc.modelimport.VisitImportRepository;
import org.joelson.turf.scorecalc.modelimport.ZoneImport;
import org.joelson.turf.turfgame.apiv5.Region;
import org.joelson.turf.turfgame.apiv5.User;
import org.joelson.turf.turfgame.apiv5.Zone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Arrays;

@Service
public class VisitImportService {

    @Autowired
    AssistImportRepository assistImportRepository;

    @Autowired
    UserImportService userImportService;

    @Autowired
    VisitImportRepository visitImportRepository;

    public VisitImport get(ZoneImport zone, Instant time) {
        return visitImportRepository.findById(new VisitImportId(zone.getZoneId(), time)).orElse(null);
    }

    public VisitImport add(
            ZoneImport zone, Instant time, UserImport user, boolean takeover, int zoneTakepoints, Zone turfZone,
            User currentOwner, User[] turfAssists) {
        if (get(zone, time) != null) {
            throw new IllegalArgumentException("Visit exists.");
        }
        Region region = turfZone.getRegion();
        VisitImport visit = visitImportRepository.save(
                new VisitImport(zone, time, user, takeover, zoneTakepoints, region.getCountry(), region.getName(),
                        turfZone.getName(), currentOwner.getName()));
        if (turfAssists != null) {
            Arrays.stream(turfAssists).forEach(turfUser -> addAssist(zone, time, turfUser));
        }
        return visit;
    }

    private void addAssist(ZoneImport zone, Instant time, User turfUser) {
        UserImport user = userImportService.getOrCreate(turfUser);
        assistImportRepository.save(new AssistImport(zone, time, user, turfUser.getName()));
    }
}
