package ws.slink.intervals;

import ws.slink.intervals.exception.InvalidShiftValueException;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.TimeZone;

/**
 * Shifted interval implementation
 */
public class ShiftedInterval implements Interval {

    protected String shift;
    protected Interval base;

    protected ShiftedInterval(Interval base, String shift) {
        validateShift(shift);
        this.shift = shift;
        this.base = base;
    }

    public static ShiftedInterval of(Interval base, String shift) {
        return new ShiftedInterval(base, shift);
    }

    // region - Interval API
    @Override
    public TimeZone timezone() {
        return getBase().timezone();
    }
    @Override
    public LocalDateTime getStart() {
        return (LocalDateTime)shifted(getBase().getStart());
    }
    @Override
    public LocalDateTime getEnd() {
        return (LocalDateTime)shifted(getBase().getEnd());
    }
    @Override
    public Instant start() {
        return (Instant)shifted(getBase().start());
    }
    @Override
    public Instant end() {
        return (Instant)shifted(getBase().end());
    }
    @Override
    public boolean contains(Instant value) {
        Instant si = (Instant)shifted(getBase().start());
        Instant ei = (Instant)shifted(getBase().end());
        return (si.isBefore(value) || si.equals(value)) && (ei.isAfter(value) || ei.equals(value));
    }
    // endregion
    // region - ShiftedInterval API
    public Interval getBase() {
        return base;
    }
    public String getShift() {
        return shift;
    }
    // endregion
    // region - helpers
    private void validateShift(String input) {
        if (input == null) {
            throw new InvalidShiftValueException("null shift value passed");
        }
        if ("".equals(input.trim())) {
            return;
        }
        try {
            if (input.startsWith("-")) {
                Duration.parse(input.substring(1));
            } else {
                Duration.parse(input);
            }
        } catch (Exception e) {
            throw new InvalidShiftValueException(e);
        }
    }
    private Temporal shifted(Temporal temporal) {
        if (getShift() == null || "".equals(getShift().trim())) {
            return temporal;
        }
        if (getShift().startsWith("-")) {
            Duration d = Duration.parse(getShift().substring(1));
            return temporal.minus(d);
        } else {
            Duration d = Duration.parse(getShift());
            return temporal.plus(d);
        }
    }
    // endregion

}