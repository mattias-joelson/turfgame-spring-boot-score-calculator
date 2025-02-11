package org.joelson.turf.scorecalc.service;

import org.joelson.turf.scorecalc.model.VisitRepository;
import org.joelson.turf.scorecalc.model.Round;
import org.joelson.turf.scorecalc.model.RoundRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import static java.time.DayOfWeek.SUNDAY;

@Service
public class RoundService {

    private static final Logger logger = LoggerFactory.getLogger(RoundService.class);

    private static final ZoneId STH_ZONE = ZoneId.of("Europe/Stockholm");
    private static final LocalDateTime DATE_2024_09_01 = LocalDateTime.of(2024, Month.SEPTEMBER, 1, 12, 0);
    private static final int ROUND_2024_09_01 = 171;

    @Autowired
    private RoundRepository roundRepository;

    @Autowired
    private VisitRepository visitRepository;

    public void updateRounds() {
        Instant firstVisitTime = visitRepository.findFirstVisitTime().orElse(null);
        if (firstVisitTime == null) {
            return;
        }
        Instant lastVisitTime = visitRepository.findLastVisitTime().orElse(null);
        if (lastVisitTime == null) {
            throw new IllegalStateException("lastVisitTime is null while firstVisitTime is" + firstVisitTime);
        }
        logger.info("firstVisitTime={}, lastVisitTime={}", firstVisitTime, lastVisitTime);

        Instant instant_2024_09_01 = DATE_2024_09_01.atZone(STH_ZONE).toInstant();
        getOrCreate(ROUND_2024_09_01, instant_2024_09_01);

        createDownToFirst(firstVisitTime);
        createUpToLast(lastVisitTime);

        List<Round> rounds = roundRepository.findAll(Sort.by(new Sort.Order(Sort.Direction.ASC, "start")));
        logger.info("rounds.size()={}, rounds.getFirst()={}, rounds.getLast()={}",
                rounds.size(), rounds.getFirst(), rounds.getLast());
    }

    private void createDownToFirst(Instant firstVisitTime) {
        int number = ROUND_2024_09_01;
        LocalDateTime firstOfMonth = DATE_2024_09_01;
        Instant start = firstOfMonth.atZone(STH_ZONE).toInstant();
        while (start.isAfter(firstVisitTime)) {
            number -= 1;
            firstOfMonth = firstOfMonth.minusMonths(1);
            LocalDateTime firstOfRound = firstOfMonth.plusDays(
                    SUNDAY.getValue() - firstOfMonth.getDayOfWeek().getValue());
            start = firstOfRound.atZone(STH_ZONE).toInstant();
            getOrCreate(number, start);
        }
    }

    private void createUpToLast(Instant lastVisitTime) {
        int number = ROUND_2024_09_01;
        LocalDateTime firstOfMonth = DATE_2024_09_01;
        Instant start = firstOfMonth.atZone(STH_ZONE).toInstant();
        while (start.isBefore(lastVisitTime)) {
            number += 1;
            firstOfMonth = firstOfMonth.plusMonths(1);
            LocalDateTime firstOfRound = firstOfMonth.plusDays(
                    SUNDAY.getValue() - firstOfMonth.getDayOfWeek().getValue());
            start = firstOfRound.atZone(STH_ZONE).toInstant();
            getOrCreate(number, start);
        }
    }

    public Round getOrCreate(int number, Instant start) {
        Round round = roundRepository.findById(number).orElse(null);
        if (round == null) {
            LocalDateTime localDateTime = LocalDateTime.ofInstant(start, STH_ZONE);
            Month month = localDateTime.getMonth();
            String monthString = month.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            String name = monthString + " " + localDateTime.getYear();
            return roundRepository.save(new Round(number, name, start));
        }
        return round;
    }
}
