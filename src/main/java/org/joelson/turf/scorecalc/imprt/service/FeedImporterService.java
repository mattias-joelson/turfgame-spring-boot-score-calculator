package org.joelson.turf.scorecalc.imprt.service;

import org.joelson.turf.scorecalc.imprt.model.UserImport;
import org.joelson.turf.scorecalc.imprt.model.VisitImport;
import org.joelson.turf.scorecalc.model.Zone;
import org.joelson.turf.scorecalc.service.ZoneImportService;
import org.joelson.turf.turfgame.FeedObject;
import org.joelson.turf.turfgame.apiv5.FeedTakeover;
import org.joelson.turf.turfgame.apiv5.User;
import org.joelson.turf.turfgame.util.DefaultFeedContentErrorHandler;
import org.joelson.turf.turfgame.util.DefaultFeedContentLoggerErrorHandler;
import org.joelson.turf.turfgame.util.FeedsReader;
import org.joelson.turf.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;

@Service
public class FeedImporterService {

    private static final Logger logger = LoggerFactory.getLogger(FeedImporterService.class);

    private final FeedsReader feedsReader;
    private final DefaultFeedContentErrorHandler errorHandler;

    private int filesSkipped = 0;
    private int filesHandled = 0;
    private int takeoversHandled = 0;
    private int takeoversAdded = 0;

    @Autowired
    UserImportService userImportService;

    @Autowired
    VisitImportService visitImportService;

    @Autowired
    ZoneImportService zoneImportService;

    public FeedImporterService() {
        errorHandler = new DefaultFeedContentLoggerErrorHandler(logger);
        feedsReader = new FeedsReader(Map.of("takeover", FeedTakeover.class), errorHandler);
    }

    public void importFeeds(String[] args) {
        for (String filename : args) {
            importFeed(filename);
        }
        if (args.length > 0) {
            logger.info("Done importing data.");
            errorHandler.messageErrorPaths(20);
        }
    }

    public void importFeed(String filename) {
        logger.info("Importing data from '{}'", filename);
        int filesSkippedBefore = filesSkipped;
        int filesHandledBefore = filesHandled;
        int takeoversHandledBefore = takeoversHandled;
        int takeoversAddedBefore = takeoversAdded;
        try {
            feedsReader.handleFeedObjectPath(Path.of(filename), this::isTakeoverV5Path, this::handleFeedObject);
        } catch (IOException e) {
            logger.error("Error importing data from '{}'", filename);
        }
        logger.info("    filesSkipped={}, filesHandled={}, takeoversHandled={}, takeoversAdded={}",
                filesSkipped - filesSkippedBefore, filesHandled - filesHandledBefore,
                takeoversHandled - takeoversHandledBefore, takeoversAdded - takeoversAddedBefore);
    }

    private boolean isTakeoverV5Path(Path path) {
        String s = path.toString();
        if (s.contains("v5") && s.contains("takeover")) {
            filesHandled += 1;
            if (filesHandled % 100 == 0) {
                logger.info("    reading path {} - filesHandled={}, takeoversHandled={}, takeoversAdded={}",
                        path, filesHandled, takeoversHandled, takeoversAdded);
            }
            return true;
        } else {
            filesSkipped += 1;
            if (filesSkipped % 1000 == 0) {
                logger.info("    skipping path {} - filesSkipped={}", path, filesSkipped);
            }
            return false;
        }
    }

    private void handleFeedObject(FeedObject feedObject) {
        if (feedObject instanceof FeedTakeover feedTakeover) {
            handleTakeover(feedTakeover);
        } else {
            throw new IllegalArgumentException("Can't handle feed object of type " + feedObject.getClass().getName());
        }
    }

    private void handleTakeover(FeedTakeover feedTakeover) {
        takeoversHandled += 1;
        Zone zone = zoneImportService.getOrCreate(feedTakeover.getZone());
        Instant time = TimeUtil.turfTimestampToInstant(feedTakeover.getTime());
        UserImport user = userImportService.getOrCreate(feedTakeover.getCurrentOwner());
        VisitImport existingVisit = visitImportService.get(zone, time);
        if (existingVisit != null) {
            if (!Objects.equals(existingVisit.getUser().getUserId(), user.getUserId())) {
                throw new IllegalArgumentException("Different owners.");
            }
            logger.trace("Skipping existing visit {}", existingVisit);
            return;
        }

        takeoversAdded += 1;
        User turfPreviousOwner = feedTakeover.getPreviousOwner();
        UserImport previousOwner = (turfPreviousOwner != null) ? userImportService.getOrCreate(turfPreviousOwner) :
                null;
        boolean takeover = (previousOwner == null) || !Objects.equals(user.getUserId(), previousOwner.getUserId());
        int zoneTakepoints = feedTakeover.getZone().getTakeoverPoints();
        visitImportService.add(zone, time, user, takeover, zoneTakepoints, feedTakeover.getZone(),
                feedTakeover.getCurrentOwner(), feedTakeover.getAssists());
    }
}
