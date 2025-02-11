package org.joelson.turf.scorecalc.imprt.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.Optional;

public interface VisitImportRepository extends JpaRepository<VisitImport, VisitImportId> {

    @Query("select v from VisitImport v where v.zone.id = :zoneId and v.time = :time")
    <T> Optional<T> findByZoneIdAndTime(Integer zoneId, Instant time, Class<T> type);
}
