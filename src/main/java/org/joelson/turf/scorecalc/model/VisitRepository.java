package org.joelson.turf.scorecalc.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.Optional;

public interface VisitRepository extends JpaRepository<Visit, VisitId> {

    @Query("select v from Visit v where v.zone.id = :zoneId and v.time = :time")
    <T> Optional<T> findByZoneIdAndTime(Integer zoneId, Instant time, Class<T> type);

    @Query("select min(v.time) from Visit v")
    Optional<Instant> findFirstVisitTime();

    @Query("select max(v.time) from Visit v")
    Optional<Instant> findLastVisitTime();
}
