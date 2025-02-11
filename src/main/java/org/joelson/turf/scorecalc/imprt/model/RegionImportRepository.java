package org.joelson.turf.scorecalc.imprt.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegionImportRepository extends JpaRepository<RegionImport, Integer> {

    <T> Optional<T> findByRegionId(Integer regionId, Class<T> type);
}
