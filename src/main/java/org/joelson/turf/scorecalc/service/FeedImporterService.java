package org.joelson.turf.scorecalc.service;

import org.joelson.turf.turfgame.apiv5.FeedTakeover;
import org.joelson.turf.turfgame.util.FeedsReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FeedImporterService {

    private static final Logger logger = LoggerFactory.getLogger(FeedImporterService.class);

    private final FeedsReader feedsReader;

    private int filesHandled = 0;
    private int filesSkipped = 0;
    private final List<String> filesWithErrors;

    public FeedImporterService() {
        feedsReader = new FeedsReader(Map.of("takeover", FeedTakeover.class));
        filesWithErrors = new ArrayList<>();
    }

    public void importFeeds(String[] args) {
        for (String filename : args) {
            importFeed(filename);
        }
        if (args.length > 0) {
            logger.info("Done importing data.");
        }
    }

    public void importFeed(String filename) {
        logger.info(String.format("Importing data from '%s'", filename));
    }
}
