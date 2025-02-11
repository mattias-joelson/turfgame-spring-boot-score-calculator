package org.joelson.turf.scorecalc.imprt.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserImportRepository extends JpaRepository<UserImport, Integer> {

    <T> Optional<T> findByUserId(Integer userId, Class<T> type);
}
