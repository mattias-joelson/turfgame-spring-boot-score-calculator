package org.joelson.turf.scorecalc.modelimport;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.Objects;

@Entity
@IdClass(AssistImportId.class)
@Table(name = "assists_import", indexes = { @Index(name = "index_assists_zone_id", columnList = "zone_id"),
        @Index(name = "index_assists_time", columnList = "time"),
        @Index(name = "index_assists_user_id", columnList = "user_id"),
        @Index(name = "index_assists_user_name", columnList = "user_name") })
public class AssistImport {

    @Id
    @ManyToOne
    @JoinColumn(name = "zone_id", updatable = false, nullable = false)
    private ZoneImport zone;

    @Id
    @Column(updatable = false, nullable = false)
    private Instant time;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", updatable = false, nullable = false)
    private UserImport user;

    @Column(name = "user_name", updatable = false, nullable = false)
    private String userName;

    protected AssistImport() {
    }

    public AssistImport(ZoneImport zone, Instant time, UserImport user, String userName) {
        this.zone = Objects.requireNonNull(zone);
        this.time = ModelConstraintsUtil.isTruncatedToSeconds(time);
        this.user = Objects.requireNonNull(user);
        this.userName = ModelConstraintsUtil.isNotEmpty(userName);
    }

    public ZoneImport getZone() {
        return zone;
    }

    public Instant getTime() {
        return time;
    }

    public UserImport getUser() {
        return user;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof AssistImport that) {
            return Objects.equals(zone, that.zone) && Objects.equals(time, that.time)
                    && Objects.equals(user, that.user);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(zone, time, user);
    }

    @Override
    public String toString() {
        return String.format("AssistImport[zoneId=%d, time=%s, userId=%d, userName=%s",
                zone.getZoneId(), time, user.getUserId(), userName);
    }
}
