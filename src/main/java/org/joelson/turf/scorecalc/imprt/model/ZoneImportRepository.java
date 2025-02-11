package org.joelson.turf.scorecalc.imprt.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ZoneImportRepository extends JpaRepository<ZoneImport, Integer> {

    <T> Optional<T> findByZoneId(Integer zoneId, Class<T> type);
}
