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
@Table(name = "zone_imports", indexes = { @Index(name = "index_zones_region_id", columnList = "region_id") })
public class ZoneImport {

    @Id
    @Column(name = "zone_id", updatable = false, nullable = false)
    private Long zoneId;

    @ManyToOne
    @JoinColumn(name = "region_id", updatable = false, nullable = false)
    private RegionImport region;

    protected ZoneImport() {
    }

    public ZoneImport(Long zoneId, RegionImport region) {
        this.zoneId = ModelConstraintsUtil.isAboveZero(zoneId);
        this.region = Objects.requireNonNull(region);
    }

    public Long getZoneId() {
        return zoneId;
    }

    public RegionImport getRegion() {
        return region;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof ZoneImport zone) {
            return Objects.equals(zoneId, zone.zoneId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(zoneId);
    }

    @Override
    public String toString() {
        return String.format("ZoneImport[zoneId=%d, regionId=%d]", zoneId, region.getRegionId());
    }
}
