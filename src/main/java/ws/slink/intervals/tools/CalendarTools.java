package ws.slink.intervals.tools;

import ws.slink.intervals.Interval;
import ws.slink.intervals.IntervalBuilder;
import ws.slink.intervals.exception.InvalidDayException;
import ws.slink.intervals.exception.InvalidMonthException;
import ws.slink.intervals.exception.InvalidYearException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjuster;

import static ws.slink.intervals.tools.Formats.DAY_FORMATTER;
import static ws.slink.intervals.tools.Formats.MONTH_FORMATTER;
import static ws.slink.intervals.tools.Formats.YEAR_FORMATTER;

public final class CalendarTools {

    private CalendarTools() {
        // private default constructor to prevent class instantiation
    }

    private static final int DAY_START_HOUR = 0;
    private static final int DAY_START_MINUTE = 0;
    private static final int DAY_START_SECOND = 0;
    private static final int DAY_START_NANO = 0;

    private static final int DAY_END_HOUR = 23;
    private static final int DAY_END_MINUTE = 59;
    private static final int DAY_END_SECOND = 59;
    private static final int DAY_END_NANO = 999999999;

    public static final TemporalAdjuster TO_START_OF_DAY = t ->
        t.with(ChronoField.HOUR_OF_DAY, DAY_START_HOUR)
         .with(ChronoField.MINUTE_OF_HOUR, DAY_START_MINUTE)
         .with(ChronoField.SECOND_OF_MINUTE, DAY_START_SECOND)
         .with(ChronoField.NANO_OF_SECOND, DAY_START_NANO)
    ;
    public static final TemporalAdjuster TO_END_OF_DAY = t ->
        t.with(ChronoField.HOUR_OF_DAY, DAY_END_HOUR)
         .with(ChronoField.MINUTE_OF_HOUR, DAY_END_MINUTE)
         .with(ChronoField.SECOND_OF_MINUTE, DAY_END_SECOND)
         .with(ChronoField.NANO_OF_SECOND, DAY_END_NANO)
    ;

    public static final LocalTime DAY_START = LocalTime.of(
        DAY_START_HOUR,
        DAY_START_MINUTE,
        DAY_START_SECOND,
        DAY_START_NANO
    );
    public static final LocalTime DAY_END = LocalTime.of(
        DAY_END_HOUR,
        DAY_END_MINUTE,
        DAY_END_SECOND,
        DAY_END_NANO
    );

    public static final LocalDateTime MIN_DATE = LocalDateTime.of(
        -999999999,
        1,
        1,
        0,
        0,
        0,
        0
    );
    public static final LocalDateTime MAX_DATE = LocalDateTime.of(
        999999999,
        12,
        31,
        23,
        59,
        59,
        999999999
    );

    @SuppressWarnings("unused")
    public static Interval yearFromString(String input) {
        return yearFromString(input, "UTC");
    }
    public static Interval yearFromString(String input, String timezone) {
        return yearFromString(input, timezone, 0);
    }
    @SuppressWarnings("unused")
    public static Interval yearFromString(String input, int offset) {
        return yearFromString(input, "UTC", offset);
    }
    public static Interval yearFromString(String input, String timezone, int offset) {
        try {
            LocalDateTime start = LocalDateTime.parse(input, YEAR_FORMATTER);
            return IntervalBuilder.year(start.getYear(), timezone, offset);
        } catch (DateTimeParseException e) {
            throw new InvalidYearException(e);
        }
    }

    @SuppressWarnings("unused")
    public static Interval monthFromString(String input) {
        return monthFromString(input, "UTC");
    }
    public static Interval monthFromString(String input, String timezone) {
        return monthFromString(input, timezone, 0);
    }
    @SuppressWarnings("unused")
    public static Interval monthFromString(String input, int offset) {
        return monthFromString(input, "UTC", offset);
    }
    public static Interval monthFromString(String input, String timezone, int offset) {
        try {
            LocalDateTime start = LocalDateTime.parse(input, MONTH_FORMATTER);
            return IntervalBuilder.month(start.getYear(), start.getMonthValue(), timezone, offset);
        } catch (DateTimeParseException e) {
            throw new InvalidMonthException(e);
        }
    }

    @SuppressWarnings("unused")
    public static Interval dayFromString(String input) {
        return dayFromString(input, "UTC");
    }
    public static Interval dayFromString(String input, String timezone) {
        return dayFromString(input, timezone, 0);
    }
    @SuppressWarnings("unused")
    public static Interval dayFromString(String input, int offset) {
        return dayFromString(input, "UTC", offset);
    }
    public static Interval dayFromString(String input, String timezone, int offset) {
        try {
            LocalDateTime start = LocalDateTime.parse(input, DAY_FORMATTER);
            return IntervalBuilder.day(start.getYear(), start.getMonthValue(), start.getDayOfMonth(), timezone, offset);
        } catch (DateTimeParseException e) {
            throw new InvalidDayException(e);
        }
    }

}
