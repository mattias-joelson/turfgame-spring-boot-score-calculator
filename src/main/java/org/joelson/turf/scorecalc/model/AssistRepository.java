package org.joelson.turf.scorecalc.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface AssistRepository extends JpaRepository<Assist, AssistId> {

    @Query("select a from Assist a where a.zone.id = :zoneId and a.time = :time and a.user.id = :userId")
    <T> Optional<T> findByZoneIdAndTimeAndUserId(Integer zoneId, Instant time, Integer userId, Class<T> type);

    @Query("select a from Assist a where a.zone.id = :zoneId and a.time = :time order by a.user.id")
    <T> List<T> findByZoneIdAndTime(Integer zoneId, Instant time, Class<T> type);

    @Query("select a from Assist a where a.zone.zoneId = :zoneId and a.time >= :start and a.time <= :end order by a"
            + ".time")
    List<Assist> getAssistsForZoneIdBetween(Integer zoneId, Instant start, Instant end);
}
