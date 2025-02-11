package org.joelson.turf.scorecalc.imprt.model;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

final class ModelConstraintsUtil {

    private ModelConstraintsUtil() throws InstantiationException {
        throw new InstantiationException("Should not be instantiated.");
    }

    public static Long isNullOrAboveZero(Long l) {
        if (l == null || l > 0L) {
            return l;
        }
        throw new IllegalArgumentException(Objects.toString(l));
    }

    public static Long isAboveZero(Long l) {
        if (Objects.requireNonNull(l) > 0L) {
            return l;
        }
        throw new IllegalArgumentException(l.toString());
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
