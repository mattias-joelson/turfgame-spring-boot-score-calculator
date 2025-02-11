package org.joelson.turf.scorecalc.service;

import org.joelson.turf.scorecalc.imprt.model.AssistImport;
import org.joelson.turf.scorecalc.imprt.model.AssistImportRepository;
import org.joelson.turf.scorecalc.model.TurfgameAssertionException;
import org.joelson.turf.scorecalc.model.User;
import org.joelson.turf.scorecalc.model.Visit;
import org.joelson.turf.scorecalc.model.VisitId;
import org.joelson.turf.scorecalc.model.VisitRepository;
import org.joelson.turf.scorecalc.model.Zone;
import org.joelson.turf.turfgame.apiv5.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Arrays;

@Service
public class VisitService {

    @Autowired
    AssistImportRepository assistImportRepository;

    @Autowired
    UserService userService;

    @Autowired
    VisitRepository visitRepository;

    public Visit get(Zone zone, Instant time) {
        return visitRepository.findById(new VisitId(zone.getZoneId(), time)).orElse(null);
    }

    public Visit add(
            Zone zone, Instant time, User user, boolean takeover, int takePoints,
            org.joelson.turf.turfgame.apiv5.Zone turfZone, org.joelson.turf.turfgame.apiv5.User currentOwner,
            org.joelson.turf.turfgame.apiv5.User[] turfAssists) {
        if (get(zone, time) != null) {
            throw new TurfgameAssertionException("Visit exists.");
        }
        Region region = turfZone.getRegion();
        Visit visit = visitRepository.save(
                new Visit(zone, time, user, takeover, takePoints, region.getCountry(), region.getName(),
                        turfZone.getName(), currentOwner.getName()));
        if (turfAssists != null) {
            Arrays.stream(turfAssists).forEach(turfUser -> addAssist(zone, time, turfUser));
        }
        return visit;
    }

    private void addAssist(Zone zone, Instant time, org.joelson.turf.turfgame.apiv5.User turfUser) {
        User user = userService.getOrCreate(turfUser);
        assistImportRepository.save(new AssistImport(zone, time, user, turfUser.getName()));
    }
}
