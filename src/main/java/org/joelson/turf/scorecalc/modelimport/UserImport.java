package org.joelson.turf.scorecalc.modelimport;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "users_import")
public class UserImport {

    @Id
    @Column(name = "user_id", nullable = false, updatable = false)
    private Long userId;

    protected UserImport() {
    }

    public UserImport(Long userId) {
        this.userId = ModelConstraintsUtil.isAboveZero(userId);
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof UserImport that) {
            return Objects.equals(userId, that.userId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId);
    }

    @Override
    public String toString() {
        return String.format("UserImport[userId=%d]", userId);
    }
}
