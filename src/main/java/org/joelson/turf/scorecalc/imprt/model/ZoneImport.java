package org.joelson.turf.scorecalc.imprt.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "zones_import", indexes = { @Index(name = "index_zones_region_id", columnList = "region_id") })
public class ZoneImport {

    @Id
    @Column(name = "zone_id", updatable = false, nullable = false)
    private Integer zoneId;

    @ManyToOne
    @JoinColumn(name = "region_id", updatable = false, nullable = false,
            foreignKey = @ForeignKey(name = "fk_regions_import_region_id"))
    private RegionImport region;

    protected ZoneImport() {
    }

    public ZoneImport(Integer zoneId, RegionImport region) {
        this.zoneId = ModelConstraintsUtil.isAboveZero(zoneId);
        this.region = Objects.requireNonNull(region);
    }

    public Integer getZoneId() {
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
        if (o instanceof ZoneImport that) {
            return Objects.equals(zoneId, that.zoneId) && Objects.equals(region, that.region);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(zoneId, region);
    }

    @Override
    public String toString() {
        return String.format("ZoneImport[zoneId=%d, regionId=%d]", zoneId, region.getRegionId());
    }
}
