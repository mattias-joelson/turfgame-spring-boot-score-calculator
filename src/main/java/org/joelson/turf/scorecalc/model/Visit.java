package org.joelson.turf.scorecalc.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.Objects;

@Entity
@IdClass(VisitId.class)
@Table(name = "visits", indexes = {
        @Index(name = "index_visits_zone_id", columnList = "zone_id"),
        @Index(name = "index_visits_time", columnList = "time"),
        @Index(name = "index_visits_user_id", columnList = "user_id"),
        @Index(name = "index_visits_country_name", columnList = "country_name"),
        @Index(name = "index_visits_region_name", columnList = "region_name"),
        @Index(name = "index_visits_zone_name", columnList = "zone_name"),
        @Index(name = "index_visits_user_name", columnList = "user_name") })
public class Visit {

    @Id
    @ManyToOne
    @JoinColumn(name = "zone_id", updatable = false, nullable = false,
            foreignKey = @ForeignKey(name = "fk_zones_zone_id"))
    private Zone zone;

    @Id
    @Column(updatable = false, nullable = false)
    private Instant time;

    @ManyToOne
    @JoinColumn(name = "user_id", updatable = false, nullable = false,
            foreignKey = @ForeignKey(name = "fk_users_user_id"))
    private User user;

    @Column(updatable = false, nullable = false)
    private boolean takeover;

    @Column(name = "take_points", updatable = false, nullable = false)
    private int takePoints;

    @Column(name = "country_name", updatable = false)
    private String countryName;

    @Column(name = "region_name", updatable = false, nullable = false)
    private String regionName;

    @Column(name = "zone_name", updatable = false, nullable = false)
    private String zoneName;

    @Column(name = "user_name", updatable = false, nullable = false)
    private String userName;

    protected Visit() {
    }

    public Visit(
            Zone zone, Instant time, User user, boolean takeover, int takePoints, String countryName,
            String regionName, String zoneName, String userName) {
        this.zone = Objects.requireNonNull(zone);
        this.time = ModelConstraintsUtil.isTruncatedToSeconds(time);
        this.user = Objects.requireNonNull(user);
        this.takeover = takeover;
        this.takePoints = takePoints;
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

    public int getTakePoints() {
        return takePoints;
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
        if (o instanceof Visit that) {
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
                "Visit[zoneId=%d, time=%s, userId=%d, takeover=%b, takePoints=%d, countryName=%s, regionName=%s, "
                        + "zoneName=%s, userName=%s]",
                zone.getZoneId(), time, user.getUserId(), takeover, takePoints, countryName, regionName, zoneName,
                userName);
    }
}
