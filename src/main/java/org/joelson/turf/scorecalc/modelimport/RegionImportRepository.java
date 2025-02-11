package org.joelson.turf.scorecalc.modelimport;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegionImportRepository extends JpaRepository<RegionImport, Long> {

    <T> Optional<T> findByRegionId(Long regionId, Class<T> type);
}
