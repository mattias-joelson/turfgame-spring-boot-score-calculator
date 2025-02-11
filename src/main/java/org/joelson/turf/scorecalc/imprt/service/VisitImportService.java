package org.joelson.turf.scorecalc.imprt.service;

import org.joelson.turf.scorecalc.imprt.model.AssistImport;
import org.joelson.turf.scorecalc.imprt.model.AssistImportRepository;
import org.joelson.turf.scorecalc.imprt.model.UserImport;
import org.joelson.turf.scorecalc.imprt.model.VisitImport;
import org.joelson.turf.scorecalc.imprt.model.VisitImportId;
import org.joelson.turf.scorecalc.imprt.model.VisitImportRepository;
import org.joelson.turf.scorecalc.model.Zone;
import org.joelson.turf.turfgame.apiv5.Region;
import org.joelson.turf.turfgame.apiv5.User;
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

    public VisitImport get(Zone zone, Instant time) {
        return visitImportRepository.findById(new VisitImportId(zone.getZoneId(), time)).orElse(null);
    }

    public VisitImport add(
            Zone zone, Instant time, UserImport user, boolean takeover, int zoneTakepoints, org.joelson.turf.turfgame.apiv5.Zone turfZone,
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

    private void addAssist(Zone zone, Instant time, User turfUser) {
        UserImport user = userImportService.getOrCreate(turfUser);
        assistImportRepository.save(new AssistImport(zone, time, user, turfUser.getName()));
    }
}
