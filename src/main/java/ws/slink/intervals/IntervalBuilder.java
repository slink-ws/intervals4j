package ws.slink.intervals;

import ws.slink.intervals.exception.InvalidIntervalException;
import ws.slink.intervals.exception.InvalidIntervalFormatException;
import ws.slink.intervals.impl.CustomInterval;
import ws.slink.intervals.exception.InvalidDayException;
import ws.slink.intervals.exception.InvalidMonthException;
import ws.slink.intervals.exception.InvalidYearException;
import ws.slink.intervals.tools.CalendarTools;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjusters;
import java.util.TimeZone;

import static ws.slink.intervals.tools.CalendarTools.MAX_DATE;
import static ws.slink.intervals.tools.CalendarTools.MIN_DATE;
import static ws.slink.intervals.tools.CalendarTools.TO_END_OF_DAY;
import static ws.slink.intervals.tools.Formats.MAX_OFFSET;

public class IntervalBuilder {

    // region - fields

    private TimeZone timezone = TimeZone.getTimeZone(ZoneOffset.UTC);
    private int offset;
    private LocalDateTime start = MIN_DATE;
    private LocalDateTime end = MAX_DATE;

    // endregion
    // region - field initialization

    public IntervalBuilder timezone(TimeZone value) {
        if (value == null) {
            throw new IllegalArgumentException("timezone should not be null");
        }
        this.timezone = value;
        return this;
    }
    public IntervalBuilder offset(int value) {
        if (Math.abs(value) > MAX_OFFSET) {
            throw new IllegalArgumentException("maximum offset is +/- " + MAX_OFFSET);
        }
        this.offset = value;
        return this;
    }
    public IntervalBuilder start(LocalDateTime value) {
        if (value == null) {
            throw new IllegalArgumentException("start date-time should not be null");
        }
        if (value.isAfter(end)) {
            throw new IllegalArgumentException("start date-time should not be after end date-time");
        }
        this.start = value;
        return this;
    }
    public IntervalBuilder start(LocalTime value) {
        if (value == null) {
            throw new IllegalArgumentException("start time should not be null");
        }
        LocalDateTime newStart = LocalDateTime.of(
            start.getYear(),
            start.getMonth(),
            start.getDayOfMonth(),
            value.getHour(),
            value.getMinute(),
            value.getSecond(),
            value.getNano()
        );
        if (newStart.isAfter(end)) {
            throw new IllegalArgumentException("start time should not be after end time");
        }
        this.start = newStart;
        return this;
    }
    public IntervalBuilder end(LocalDateTime value) {
        if (value == null) {
            throw new IllegalArgumentException("end date-time should not be null");
        }
        if (value.isBefore(start)) {
            throw new IllegalArgumentException("end date-time should not be before start date-time");
        }
        this.end = value;
        return this;
    }
    public IntervalBuilder end(LocalTime value) {
        if (value == null) {
            throw new IllegalArgumentException("end time should not be null");
        }
        LocalDateTime newEnd = LocalDateTime.of(
            end.getYear(),
            end.getMonth(),
            end.getDayOfMonth(),
            value.getHour(),
            value.getMinute(),
            value.getSecond(),
            value.getNano()
        );
        if (newEnd.isBefore(start)) {
            throw new IllegalArgumentException("end date-time should not be before start date-time");
        }
        this.end = newEnd;
        return this;
    }

    // endregion
    // region - interval construction

    public Interval build() {
        return new CustomInterval(timezone, start, end, offset);
    }

    // region -> custom interval builders: year

    public static Year year(int year) {
        return year(year, "UTC", 0);
    }
    public static Year year(int year, String timezone) {
        return year(year, timezone, 0);
    }
    public static Year year(int year, int offset) {
        return year(year, "UTC", offset);
    }
    public static Year year(int year, String timezone, int offset) {
        try {
            LocalDateTime start = LocalDateTime.of(year, 1, 1, 0, 0, 0, 0);
            LocalDateTime end = start.with(TemporalAdjusters.lastDayOfYear()).with(TO_END_OF_DAY);
            return new Year(TimeZone.getTimeZone(timezone), start, end, offset);
        } catch (DateTimeException e) {
            throw new InvalidYearException(e.getMessage());
        }
    }

    public static Month month(int year, int month) {
        return month(year, month, "UTC", 0);
    }
    public static Month month(int year, int month, String timezone) {
        return month(year, month, timezone, 0);
    }
    public static Month month(int year, int month, int offset) {
        return month(year, month, "UTC", offset);
    }
    public static Month month(int year, int month, String timezone, int offset) {
        try {
            LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0, 0, 0);
            LocalDateTime end = start.with(TemporalAdjusters.lastDayOfMonth()).with(TO_END_OF_DAY);
            return new Month(TimeZone.getTimeZone(timezone), start, end, offset);
        } catch (DateTimeException e) {
            throw new InvalidMonthException(e.getMessage());
        }
    }

    public static Day day(int year, int month, int day) {
        return day(year, month, day, "UTC", 0);
    }
    public static Day day(int year, int month, int day, String timezone) {
        return day(year, month, day, timezone, 0);
    }
    public static Day day(int year, int month, int day, int offset) {
        return day(year, month, day, "UTC", offset);
    }
    public static Day day(int year, int month, int day, String timezone, int offset) {
        try {
            LocalDateTime start = LocalDateTime.of(year, month, day, 0, 0, 0, 0);
            LocalDateTime end = start.with(TO_END_OF_DAY);
            return new Day(TimeZone.getTimeZone(timezone), start, end, offset);
        } catch (DateTimeException e) {
            throw new InvalidDayException(e.getMessage());
        }
    }

    // endregion

    // endregion

    // region - interval parsers

    public static Interval parse(String input) {
        return parse(input, "UTC");
    }
    public static Interval parse(String input, String timezone) {
        return parse(input, timezone, 0);
    }
    public static Interval parse(String input, int offset) {
        return parse(input, "UTC", offset);
    }
    public static Interval parse(String input, String timezone, int offset) {
        try {
            return doParse(input, timezone, offset);
        } catch (InvalidIntervalException e) {
            throw new InvalidIntervalFormatException(input);
        }
    }
    private static Interval doParse(String input, String timezone, int offset) {
        try {
            return CalendarTools.yearFromString(input, timezone, offset);
        } catch (InvalidYearException e1) {
            try {
                return CalendarTools.monthFromString(input, timezone, offset);
            } catch (InvalidMonthException e2) {
                return CalendarTools.dayFromString(input, timezone, offset);
            }
        }
    }

    // endregion

}
