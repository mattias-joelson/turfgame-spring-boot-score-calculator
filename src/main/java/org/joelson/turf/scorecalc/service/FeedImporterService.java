package org.joelson.turf.scorecalc.service;

import org.joelson.turf.turfgame.FeedObject;
import org.joelson.turf.turfgame.apiv5.FeedTakeover;
import org.joelson.turf.turfgame.util.DefaultFeedContentErrorHandler;
import org.joelson.turf.turfgame.util.DefaultFeedContentLoggerErrorHandler;
import org.joelson.turf.turfgame.util.FeedsPathComparator;
import org.joelson.turf.turfgame.util.FeedsReader;
import org.joelson.turf.util.FilesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

@Service
public class FeedImporterService {

    private static final Logger logger = LoggerFactory.getLogger(FeedImporterService.class);

    private final FeedsReader feedsReader;
    private final DefaultFeedContentErrorHandler errorHandler;

    private int filesHandled = 0;
    private int takeovers = 0;

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
        int filesHandledBefore = filesHandled;
        int takeoversBefore = takeovers;
        try {
            FilesUtil.forEachFile(Path.of(filename), true, new FeedsPathComparator(), this::addFeedsObjects);
        } catch (IOException e) {
            logger.error("Error importing data from '{}'", filename);
        }
        int filesHandledAfter = filesHandled;
        int takeOversAfter = takeovers;
        logger.info("    files handled: {}, takeovers: {}",
                filesHandledAfter - filesHandledBefore,
                takeOversAfter - takeoversBefore);
    }

    private void addFeedsObjects(Path path) {
        try {
            feedsReader.handleFeedObjectPath(path, this::logEvery1000thPath, this::handleFeedObject);
        } catch (IOException e) {
            logger.error("Error importing data from '{}'", path);
        }
    }

    private void logEvery1000thPath(Path path) {
        if (filesHandled % 1000 == 0) {
            logger.info("    reading path {}", path);
        }
        filesHandled += 1;
    }

    private void handleFeedObject(FeedObject feedObject) {
        if (feedObject instanceof FeedTakeover feedTakeover) {
            handleTakeover(feedTakeover);
        } else {
            throw new IllegalArgumentException("Can't handle feed object of type " + feedObject.getClass().getName());
        }
    }

    private void handleTakeover(FeedTakeover feedTakeover) {
        takeovers += 1;
    }
}
