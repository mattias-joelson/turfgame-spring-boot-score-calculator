package org.joelson.turf.scorecalc.modelimport;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "regions_import", indexes = { @Index(name = "index_regions_country_id", columnList = "country_id") })
public class RegionImport {

    @Id
    @Column(name = "region_id", nullable = false, updatable = false)
    private Long regionId;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = true, updatable = false)
    private CountryImport country;

    protected RegionImport() {
    }

    public RegionImport(Long regionId, CountryImport country) {
        this.regionId = ModelConstraintsUtil.isAboveZero(regionId);
        this.country = country;
    }

    public Long getRegionId() {
        return regionId;
    }

    public CountryImport getCountry() {
        return country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof RegionImport that) {
            return Objects.equals(regionId, that.regionId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(regionId);
    }

    @Override
    public String toString() {
        return String.format("RegionImport[regionId=%d, countryId=%s]",
                regionId, (country != null) ? country.getCountry() : null);
    }
}
