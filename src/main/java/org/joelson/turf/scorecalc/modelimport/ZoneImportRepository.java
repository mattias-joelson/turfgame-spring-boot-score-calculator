package org.joelson.turf.scorecalc.modelimport;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ZoneImportRepository extends JpaRepository<ZoneImport, Long> {

    <T> Optional<T> findByZoneId(Long zoneId, Class<T> type);
}
