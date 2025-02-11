package org.joelson.turf.scorecalc.imprt.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.joelson.turf.scorecalc.model.ModelConstraintsUtil;
import org.joelson.turf.scorecalc.model.User;
import org.joelson.turf.scorecalc.model.Zone;

import java.time.Instant;
import java.util.Objects;

@Entity
@IdClass(VisitImportId.class)
@Table(name = "visits_import", indexes = {
        @Index(name = "index_visits_zone_id", columnList = "zone_id"),
        @Index(name = "index_visits_time", columnList = "time"),
        @Index(name = "index_visits_user_id", columnList = "user_id"),
        @Index(name = "index_visits_country_name", columnList = "country_name"),
        @Index(name = "index_visits_region_name", columnList = "region_name"),
        @Index(name = "index_visits_zone_name", columnList = "zone_name"),
        @Index(name = "index_visits_user_name", columnList = "user_name") })
public class VisitImport {

    @Id
    @ManyToOne
    @JoinColumn(name = "zone_id", updatable = false, nullable = false,
            foreignKey = @ForeignKey(name = "fk_zones_imports_zone_id"))
    private Zone zone;

    @Id
    @Column(updatable = false, nullable = false)
    private Instant time;

    @ManyToOne
    @JoinColumn(name = "user_id", updatable = false, nullable = false,
            foreignKey = @ForeignKey(name = "fk_users_import_user_id"))
    private User user;

    @Column(updatable = false, nullable = false)
    private boolean takeover;

    @Column(name = "zone_takepoints", updatable = false, nullable = false)
    private int zoneTakepoints;

    @Column(name = "country_name", updatable = false)
    private String countryName;

    @Column(name = "region_name", updatable = false, nullable = false)
    private String regionName;

    @Column(name = "zone_name", updatable = false, nullable = false)
    private String zoneName;

    @Column(name = "user_name", updatable = false, nullable = false)
    private String userName;

    protected VisitImport() {
    }

    public VisitImport(
            Zone zone, Instant time, User user, boolean takeover, int zoneTakepoints, String countryName,
            String regionName, String zoneName, String userName) {
        this.zone = Objects.requireNonNull(zone);
        this.time = ModelConstraintsUtil.isTruncatedToSeconds(time);
        this.user = Objects.requireNonNull(user);
        this.takeover = takeover;
        this.zoneTakepoints = zoneTakepoints;
        this.countryName = ModelConstraintsUtil.isNullOrNotEmpty(countryName);
        this.regionName = ModelConstraintsUtil.isNotEmpty(regionName);
        this.zoneName = ModelConstraintsUtil.isNotEmpty(zoneName);
        this.userName = ModelConstraintsUtil.isNotEmpty(userName);
    }

    public Zone getZone() {
        return zone;
    }

    public Instant getTime() {
        return time;
    }

    public User getUser() {
        return user;
    }

    public boolean isTakeover() {
        return takeover;
    }

    public int getZoneTakepoints() {
        return zoneTakepoints;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getRegionName() {
        return regionName;
    }

    public String getZoneName() {
        return zoneName;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof VisitImport that) {
            return Objects.equals(zone, that.zone) && Objects.equals(time, that.time);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(zone, time);
    }

    @Override
    public String toString() {
        return String.format(
                "VisitImport[zoneId=%d, time=%s, userId=%d, takeover=%b, zoneTakepoints=%d, countryName=%s, "
                        + "regionName=%s, zoneName=%s, userName=%s]",
                zone.getZoneId(), time, user.getUserId(), takeover, zoneTakepoints, countryName, regionName, zoneName,
                userName);
    }
}
