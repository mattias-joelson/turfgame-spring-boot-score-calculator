package org.joelson.turf.scorecalc.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface VisitRepository extends JpaRepository<Visit, VisitId> {

    @Query("select v from Visit v where v.zone.id = :zoneId and v.time = :time")
    <T> Optional<T> findByZoneIdAndTime(Integer zoneId, Instant time, Class<T> type);

    @Query("select min(v.time) from Visit v")
    Optional<Instant> findFirstVisitTime();

    @Query("select max(v.time) from Visit v")
    Optional<Instant> findLastVisitTime();

    @Query("select v from Visit v where v.time >= :start and v.time <= :end")
    List<Visit> getVisitsBetween(Instant start, Instant end);

    @Query("select distinct(v.zone.zoneId) from Visit v where v.time >= :start and v.time <= :end order by v.zone"
            + ".zoneId")
    List<Integer> getDistinctZoneIdsBetween(Instant start, Instant end);

    @Query("select v from Visit v where v.zone.zoneId = :zoneId and v.time >= :start and v.time <= :end order by v"
            + ".time")
    List<Visit> getVisitsForZoneIdBetween(Integer zoneId, Instant start, Instant end);
}
