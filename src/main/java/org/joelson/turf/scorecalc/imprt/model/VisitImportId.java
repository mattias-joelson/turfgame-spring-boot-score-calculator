package org.joelson.turf.scorecalc.imprt.model;

import java.time.Instant;
import java.util.Objects;

public class VisitImportId {

    private Integer zone;
    private Instant time;

    protected VisitImportId() {
    }

    public VisitImportId(Integer zone, Instant time) {
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
        if (o instanceof VisitImportId visitId) {
            return Objects.equals(zone, visitId.zone) && Objects.equals(time, visitId.time);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(zone, time);
    }

    @Override
    public String toString() {
        return String.format("VisitImportId[zone=%d, time=%s]", zone, time);
    }
}
