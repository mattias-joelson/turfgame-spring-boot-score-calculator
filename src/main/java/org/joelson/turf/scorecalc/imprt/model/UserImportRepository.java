package org.joelson.turf.scorecalc.imprt.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserImportRepository extends JpaRepository<UserImport, Long> {

    <T> Optional<T> findByUserId(Long userId, Class<T> type);
}
