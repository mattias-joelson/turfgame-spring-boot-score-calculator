package org.joelson.turf.scorecalc.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "zones", indexes = { @Index(name = "index_zones_region_id", columnList = "region_id"),
        @Index(name = "index_zones_country", columnList = "country") })
public class Zone {

    @Id
    @Column(name = "zone_id", updatable = false, nullable = false)
    private Integer zoneId;

    @Column(name = "region_id", updatable = false, nullable = false)
    private Integer regionId;

    @Column(updatable = false)
    private String country;

    protected Zone() {
    }

    public Zone(Integer zoneId, Integer regionId, String country) {
        this.zoneId = ModelConstraintsUtil.isAboveZero(zoneId);
        this.regionId = ModelConstraintsUtil.isAboveZero(regionId);
        this.country = ModelConstraintsUtil.isNullOrNotEmpty(country);
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Zone that) {
            return Objects.equals(zoneId, that.zoneId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(zoneId);
    }

    @Override
    public String toString() {
        return String.format("Zone[zoneId=%d, regionId=%d, country=%s]",
                zoneId, regionId, (country != null) ? country : null);
    }
}
