package org.joelson.turf.scorecalc;

import org.joelson.turf.scorecalc.imprt.service.FeedImporterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TurfScoreCalculatorApplication {

    private static final Logger logger = LoggerFactory.getLogger(TurfScoreCalculatorApplication.class);

    @Autowired
    FeedImporterService feedImporterService;

    public static void main(String[] args) {
        SpringApplication.run(TurfScoreCalculatorApplication.class, args);
    }

    @Bean
    public CommandLineRunner argumentHandler(ApplicationContext ctx) {
        return args -> {
            // arguments passed through -Dspring-boot.run.arguments="test1 test2"
            logArray("Program arguments:", "No program arguments.", args);
            feedImporterService.importFeeds(args);
        };
    }

    private void logArray(String hasElements, String noElements, String[] strings) {
        if (strings.length > 0) {
            logger.info(hasElements);
            int maxLength = String.valueOf(strings.length).length();
            String format = String.format("  [%%%dd] %%s", maxLength);
            for (int i = 0; i < strings.length; i += 1) {
                logger.info(String.format(format, i, strings[i]));
            }
        } else {
            logger.info(noElements);
        }
    }
}
