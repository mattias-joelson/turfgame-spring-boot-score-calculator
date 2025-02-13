package org.joelson.turf.scorecalc.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "rounds", indexes = { @Index(name = "index_name", columnList = "name"),
        @Index(name = "index_start", columnList = "start") })
public class Round {

    @Id
    @Column(updatable = false, nullable = false)
    public Integer number; // "round-number"

    @Column(updatable = false, nullable = false)
    public String name;

    @Column(updatable = false, nullable = false)
    public Instant start;

    protected Round() {
    }

    public Round(Integer number, String name, Instant start) {
        this.number = ModelConstraintsUtil.isAboveZero(number);
        this.name = ModelConstraintsUtil.isNotEmpty(name);
        this.start = ModelConstraintsUtil.isTruncatedToSeconds(start);
    }

    public Integer getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public Instant getStart() {
        return start;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Round that) {
            return Objects.equals(number, that.number);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(number);
    }

    @Override
    public String toString() {
        return String.format("Round[number=%s, name=%s, start=%s]", number, name, start);
    }
}
