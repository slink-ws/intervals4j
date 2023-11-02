package ws.slink.intervals.tools;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

/**
 * Interval-related constants
 */
public final class Formats {

    private Formats() {
        // private default constructor to prevent class instantiation
    }

    public static final int MAX_OFFSET = 23;


    public static final String YEAR_FORMAT = "yyyy";
    public static final String MONTH_FORMAT = "yyyy-MM";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";


    public static final DateTimeFormatter YEAR_FORMATTER = new DateTimeFormatterBuilder()
        .appendPattern(YEAR_FORMAT)
        .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
        .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
        .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
        .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
        .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
        .parseDefaulting(ChronoField.NANO_OF_SECOND, 0)
        .toFormatter();
    public static final DateTimeFormatter MONTH_FORMATTER = new DateTimeFormatterBuilder()
        .appendPattern(MONTH_FORMAT)
        .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
        .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
        .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
        .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
        .parseDefaulting(ChronoField.NANO_OF_SECOND, 0)
        .toFormatter();
    public static final DateTimeFormatter DAY_FORMATTER = new DateTimeFormatterBuilder()
        .appendPattern(DATE_FORMAT)
        .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
        .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
        .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
        .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
        .parseDefaulting(ChronoField.NANO_OF_SECOND, 0)
        .toFormatter();
    @SuppressWarnings("unused")
    public static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
        .appendPattern(DATE_TIME_FORMAT)
        .toFormatter();

}
