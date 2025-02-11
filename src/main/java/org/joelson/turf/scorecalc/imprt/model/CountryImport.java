package org.joelson.turf.scorecalc.imprt.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "countries_import")
public class CountryImport {

    @Id
    @Column(updatable = false, nullable = false)
    private String country;

    protected CountryImport() {
    }

    public CountryImport(String countryId) {
        this.country = ModelConstraintsUtil.isNotEmpty(countryId);
    }

    public String getCountry() {
        return country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof CountryImport that) {
            return Objects.equals(country, that.country);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(country);
    }

    @Override
    public String toString() {
        return String.format("CountryImport[countryId=%s]", country);
    }
}
