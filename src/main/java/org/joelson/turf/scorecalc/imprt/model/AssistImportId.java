package org.joelson.turf.scorecalc.imprt.model;

import java.time.Instant;
import java.util.Objects;

public class AssistImportId {

    private Long zone;
    private Instant time;
    private Long user;

    protected AssistImportId() {
    }

    public AssistImportId(Long zone, Instant time, Long user) {
        setZone(zone);
        setTime(time);
        setUser(user);
    }

    public Long getZone() {
        return zone;
    }

    public void setZone(Long zone) {
        this.zone = ModelConstraintsUtil.isAboveZero(zone);
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = ModelConstraintsUtil.isTruncatedToSeconds(time);
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = ModelConstraintsUtil.isAboveZero(user);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof AssistImportId visitId) {
            return Objects.equals(zone, visitId.zone) && Objects.equals(user, visitId.user)
                    && Objects.equals(time, visitId.time);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(zone, user, time);
    }

    @Override
    public String toString() {
        return String.format("AssistImportId[zone=%d, time=%s, user=%d]", zone, time, user);
    }
}
