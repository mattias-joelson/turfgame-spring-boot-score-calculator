package org.joelson.turf.scorecalc.imprt.model;

import java.time.Instant;
import java.util.Objects;

public class AssistImportId {

    private Integer zone;
    private Instant time;
    private Integer user;

    protected AssistImportId() {
    }

    public AssistImportId(Integer zone, Instant time, Integer user) {
        setZone(zone);
        setTime(time);
        setUser(user);
    }

    public Integer getZone() {
        return zone;
    }

    public void setZone(Integer zone) {
        this.zone = ModelConstraintsUtil.isAboveZero(zone);
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = ModelConstraintsUtil.isTruncatedToSeconds(time);
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = ModelConstraintsUtil.isAboveZero(user);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof AssistImportId that) {
            return Objects.equals(zone, that.zone) && Objects.equals(user, that.user)
                    && Objects.equals(time, that.time);
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
