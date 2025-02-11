package org.joelson.turf.scorecalc.imprt.model;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

final class ModelConstraintsUtil {

    private ModelConstraintsUtil() throws InstantiationException {
        throw new InstantiationException("Should not be instantiated.");
    }

    public static Integer isNullOrAboveZero(Integer i) {
        if (i == null || i > 0) {
            return i;
        }
        throw new IllegalArgumentException(Objects.toString(i));
    }

    public static Integer isAboveZero(Integer i) {
        if (Objects.requireNonNull(i) > 0) {
            return i;
        }
        throw new IllegalArgumentException(i.toString());
    }

    public static Instant isTruncatedToSeconds(Instant instant) {
        if (Objects.requireNonNull(instant).truncatedTo(ChronoUnit.SECONDS).equals(instant)) {
            return instant;
        }
        throw new IllegalArgumentException(instant.toString());
    }

    public static String isNullOrNotEmpty(String s) {
        if (s == null || !s.isEmpty()) {
            return s;
        }
        throw new IllegalArgumentException(s);
    }

    public static String isNotEmpty(String s) {
        if (!Objects.requireNonNull(s).isEmpty()) {
            return s;
        }
        throw new IllegalArgumentException(s);
    }
}
