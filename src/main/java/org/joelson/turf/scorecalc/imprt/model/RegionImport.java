package org.joelson.turf.scorecalc.imprt.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.joelson.turf.scorecalc.model.ModelConstraintsUtil;

import java.util.Objects;

@Entity
@Table(name = "regions_import", indexes = { @Index(name = "index_regions_country_id", columnList = "country_id") })
public class RegionImport {

    @Id
    @Column(name = "region_id", nullable = false, updatable = false)
    private Integer regionId;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = true, updatable = false,
            foreignKey = @ForeignKey(name = "fk_countries_import_country"))
    private CountryImport country;

    protected RegionImport() {
    }

    public RegionImport(Integer regionId, CountryImport country) {
        this.regionId = ModelConstraintsUtil.isAboveZero(regionId);
        this.country = country;
    }

    public Integer getRegionId() {
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
            return Objects.equals(regionId, that.regionId) && Objects.equals(country, that.country);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(regionId, country);
    }

    @Override
    public String toString() {
        return String.format("RegionImport[regionId=%d, country=%s]",
                regionId, (country != null) ? country.getCountry() : null);
    }
}
