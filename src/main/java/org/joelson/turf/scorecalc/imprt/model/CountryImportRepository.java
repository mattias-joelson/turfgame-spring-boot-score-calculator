package org.joelson.turf.scorecalc.imprt.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryImportRepository extends JpaRepository<CountryImport, String> {

    <T> Optional<T> findByCountry(String country, Class<T> type);
}
