package ws.slink.intervals.impl;

import ws.slink.intervals.Interval;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

import static ws.slink.intervals.tools.Formats.DATE_TIME_FORMAT;
import static ws.slink.intervals.tools.Formats.MAX_OFFSET;

public class CustomInterval implements Interval {

    // region - fields

    protected TimeZone timezone;
    protected LocalDateTime start;
    protected LocalDateTime end;

    // endregion
    // region - initialization

    public CustomInterval(TimeZone timezone, LocalDateTime start, LocalDateTime end) {
        this(timezone, start, end, 0);
    }
    public CustomInterval(TimeZone timezone, LocalDateTime start, LocalDateTime end, int offset) {
        if (timezone == null) {
            throw new IllegalArgumentException("timezone should not be null");
        }
        if (start == null) {
            throw new IllegalArgumentException("start timestamp should not be null");
        }
        if (end == null) {
            throw new IllegalArgumentException("end timestamp should not be null");
        }
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("end timestamp should be after start timestamp");
        }
        if (Math.abs(offset) > MAX_OFFSET) {
            throw new IllegalArgumentException("maximum offset is +/- " + MAX_OFFSET);
        }
        this.timezone = timezone;
        this.start = start;
        this.end = end;
        if (offset != 0) {
            this.start = this.start.plusHours(offset);
            this.end = this.end.plusHours(offset);
        }
    }

    // endregion
    // region - getters

    @Override public TimeZone timezone() {
        return this.timezone;
    }
    @Override public LocalDateTime getStart() {
        return start;
    }
    @Override public LocalDateTime getEnd() {
        return end;
    }
    @Override public Instant start() {
        return start.atZone(timezone.toZoneId()).toInstant();
    }
    @Override public Instant end() {
        return end.atZone(timezone.toZoneId()).toInstant();
    }

    // endregion
    // region - checkers

    @Override
    public boolean contains(Instant value) {
        Instant si = start();
        Instant ei = end();
        return (si.isBefore(value) || si.equals(value)) && (ei.isAfter(value) || ei.equals(value));
    }

    // endregion
    // region - misc

    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        return formatter.format(start) + " - " +
            formatter.format(end) + " " +
            timezone.getID();
    }

    // endregion

}
