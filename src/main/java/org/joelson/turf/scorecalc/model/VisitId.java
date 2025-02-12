package org.joelson.turf.scorecalc.model;

import java.time.Instant;
import java.util.Objects;

public class VisitId {

    private Integer zone;
    private Instant time;

    protected VisitId() {
    }

    public VisitId(Integer zone, Instant time) {
        setZone(zone);
        setTime(time);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof VisitId that) {
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
        return String.format("VisitId[zone=%s, time=%s]", zone, time);
    }
}
