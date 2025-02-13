package org.joelson.turf.scorecalc.service;

import org.joelson.turf.scorecalc.model.Assist;
import org.joelson.turf.scorecalc.model.Round;
import org.joelson.turf.scorecalc.model.Visit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ScoreService {

    private static final Logger logger = LoggerFactory.getLogger(ScoreService.class);

    @Autowired
    RoundService roundService;

    @Autowired
    VisitService visitService;

    public boolean calculateRoundScores(int roundNumber) {
        Round round = roundService.get(roundNumber);
        if (round == null) {
            logger.error("Round {} does not exits.");
            return false;
        }
        Instant start = round.getStart();
        Round nextRound = roundService.get(roundNumber + 1);
        if (nextRound == null) {
            logger.error("Round {} following {} does not exist.", roundNumber + 1, roundNumber);
            return false;
        }
        Instant end = nextRound.getStart();

        logger.info("Round {} from {} to {}", roundNumber, start, end);

        List<Integer> zonesIds = visitService.getDistinctZonesBetween(start, end);

        logger.info("Round {} zonesIds.size()={}", roundNumber, zonesIds.size());

        try (PrintWriter out = new PrintWriter(new FileWriter("foo." + roundNumber + ".csv"))) {
            int prints = 0;
            for (Integer zoneId : zonesIds) {
                prints += 1;
                List<Visit> visits = visitService.getVisitsForZoneIdBetween(zoneId, start, end);
                List<Assist> assists = visitService.getAssistsForZoneIdBetween(zoneId, start, end);
                int tp = visits.getFirst().getTakePoints();
                //int actual = calcRoundScore(tp, getPph(tp), visits, assists,
                //        end);//calcActualRoundScore(visits, assists, end);
                int t65 = calcRoundScore(65, 9, visits, assists, end);
                int t80 = calcRoundScore(80, 8, visits, assists, end);
                int t95 = calcRoundScore(95, 7, visits, assists, end);
                int t110 = calcRoundScore(110, 6, visits, assists, end);
                int t125 = calcRoundScore(125, 5, visits, assists, end);
                int t140 = calcRoundScore(140, 4, visits, assists, end);
                int t155 = calcRoundScore(155, 3, visits, assists, end);
                int t170 = calcRoundScore(170, 2, visits, assists, end);
                int t185 = calcRoundScore(185, 1, visits, assists, end);
                int t250 = calcRoundScore(250, 0, visits, assists, end);
                String name = visits.getLast().getZoneName();
//                if (t65 > t80 || t65 > t95 || t65 > t110 || t65 > t125 || t65 > t140 || t65 > t155 || t65 > t170
//                        || t65 > t185) {
//                    name = "***" + name + "***";
//                }
                /*logger.info(
                        "    zoneId={}, name={}, visits.size={}, assists.size()={}, tp={}, actual={}, t65={}, t80={},"
                                + " t95={}, t110={}, t125={}, t140={}, t155={}, t170={}, t185={}, t250={}",
                        zoneId, name, visits.size(), assists.size(), tp, 0, t65, t80, t95, t110, t125, t140, t155,
                        t170, t185, t250);*/
                if (prints % 1000 == 0) {
                    logger.info("    [{}] {}", prints, name);
                }
                out.printf("%s;%s;%d;%d;%d;%d;%d;%d;%d;%d;%d;%d;%d;%d;%d%n",
                        zoneId, name, visits.size(), assists.size(), tp, t65, t80, t95, t110, t125, t140, t155, t170,
                        t185, t250);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    private int calcActualRoundScore0(List<Visit> visits, List<Assist> assists, Instant end) {
        // revisit
        int score = 50;
        Visit visit = visits.getFirst();
        for (int i = 1; i < visits.size(); i += 1) {
            int tp = visit.getTakePoints();
            int pph = getPph(tp);
            Visit nextVist = visits.get(i);
            Duration duration = Duration.between(visit.getTime(), nextVist.getTime());
            long hours = duration.toHours();
            long seconds = duration.toSeconds() - hours * 3600;
            score += tp + (int) hours * pph + ((int) seconds * pph) / 3600;
            visit = nextVist;
        }
        int tp = visit.getTakePoints();
        int pph = getPph(tp);
        Duration duration = Duration.between(visit.getTime(), end);
        long hours = duration.toHours();
        long seconds = duration.toSeconds() - hours * 3600;
        score += tp + (int) hours * pph + ((int) seconds * pph) / 3600;
        // TODO proper assist tp, neutralizer
        score += assists.size() * visits.getFirst().getTakePoints();
        return score;
    }

    private int calcActualRoundScore1(List<Visit> visits, List<Assist> assists, Instant end) {
        // revisit
        Map<Instant, Long> assistMap = assists.stream().map(Assist::getTime)
                .collect(Collectors.groupingBy(Function.identity(), HashMap::new, Collectors.counting()));
        int score = 50;
        Instant time = null;
        int pph = 0;
        score += 50 * assistMap.get(visits.getFirst().getTime()).intValue();
        for (Visit visit : visits) {
            int tp = visit.getTakePoints();
            Instant visitTime = visit.getTime();
            score += tp * assistMap.get(visitTime).intValue();
            if (visit.isTakeover()) {
                score += tp;
                if (time != null) {
                    score += calcPph(time, visitTime, pph);
                }
                time = visitTime;
                pph = getPph(visit.getTakePoints());
            } else {
                score += tp / 2;
            }
        }
        score += calcPph(time, end, pph);
        return score;
    }

    private static int calcPph(Instant start, Instant end, int pph) {
        Duration duration = Duration.between(start, end);
        long hours = duration.toHours();
        long seconds = duration.toSeconds() - hours * 3600;
        return (int) hours * pph + ((int) seconds * pph) / 3600;
    }

    private static int getPph(int tp) {
        int pph = switch (tp) {
            case 65 -> 9;
            case 80 -> 8;
            case 95 -> 7;
            case 110 -> 6;
            case 125 -> 5;
            case 140 -> 4;
            case 155 -> 3;
            case 170 -> 2;
            case 185 -> 1;
            default -> throw new IllegalStateException("Unexpected value: " + tp);
        };
        return pph;
    }

    private int calcRoundScore(int tp, int pph, List<Visit> visits, List<Assist> assists, Instant end) {
        int score = tp * assists.size();
        Instant time = null;
        for (Visit visit : visits) {
            Instant visitTime = visit.getTime();
            if (visit.isTakeover()) {
                score += tp;
                if (time != null) {
                    score += calcPph(time, visitTime, pph);
                } else {
                    score += 50 * (1 + (int) assists.stream().map(Assist::getTime).filter(t -> t == visitTime).count());
                }
                time = visitTime;
            } else {
                score += tp / 2;
            }
        }
        score += calcPph(time, end, pph);
        return score;
    }
}
