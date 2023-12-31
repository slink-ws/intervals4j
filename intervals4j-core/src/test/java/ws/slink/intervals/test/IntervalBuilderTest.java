package ws.slink.intervals.test;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import ws.slink.intervals.Day;
import ws.slink.intervals.Interval;
import ws.slink.intervals.IntervalBuilder;
import ws.slink.intervals.Month;
import ws.slink.intervals.Year;
import ws.slink.intervals.exception.InvalidDayException;
import ws.slink.intervals.exception.InvalidIntervalFormatException;
import ws.slink.intervals.exception.InvalidMonthException;
import ws.slink.intervals.exception.InvalidYearException;
import ws.slink.intervals.tools.CalendarTools;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static ws.slink.intervals.test.common.Assertions.assertBuilderDate;
import static ws.slink.intervals.test.common.Assertions.assertBuilderDateTime;
import static ws.slink.intervals.test.common.Assertions.assertBuilderOffset;
import static ws.slink.intervals.test.common.Assertions.assertBuilderTime;
import static ws.slink.intervals.test.common.Assertions.assertBuilderTimezone;
import static ws.slink.intervals.test.common.Assertions.assertGivenDateTime;
import static ws.slink.intervals.test.common.Assertions.assertGivenTimezone;
import static ws.slink.intervals.test.common.Assertions.assertUtcTimezone;
import static ws.slink.intervals.test.common.TestConfig.TEST_END;
import static ws.slink.intervals.test.common.TestConfig.TEST_START;
import static ws.slink.intervals.test.common.TestConfig.TEST_TIMEZONE;
import static ws.slink.intervals.test.common.TestConfig.TEST_TIMEZONE_STR;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IntervalBuilderTest {

    private IntervalBuilder builder;

    @Before
    public void init() {
        builder = new IntervalBuilder();
    }

    // region - 01: initialize builder & fields

    @Test
    public void t0101_can_create_interval_builder() {
        assertNotNull(builder);
    }
    @Test
    public void t0102_builder_can_set_timezone() {
        assertBuilderTimezone(builder, TimeZone.getTimeZone(ZoneOffset.UTC));
        builder.timezone(TEST_TIMEZONE);
        assertBuilderTimezone(builder, TEST_TIMEZONE);
    }
    @Test(expected = IllegalArgumentException.class)
    public void t0103_builder_can_not_set_null_timezone() {
        builder.timezone(null);
    }
    @Test
    public void t0104_builder_can_set_positive_offset() {
        assertBuilderOffset(builder, 0);
        builder.offset(1);
        assertBuilderOffset(builder, 1);
    }
    @Test
    public void t0105_builder_can_set_negative_offset() {
        assertBuilderOffset(builder, 0);
        builder.offset(-1);
        assertBuilderOffset(builder, -1);
    }
    @Test(expected = IllegalArgumentException.class)
    public void t0106_builder_can_not_set_invalid_positive_offset() {
        builder.offset(24);
    }
    @Test(expected = IllegalArgumentException.class)
    public void t0107_builder_can_not_set_invalid_negative_offset() {
        builder.offset(-24);
    }
    @Test
    public void t0108_builder_can_set_start_time() {
        assertBuilderTime(builder, "start", CalendarTools.DAY_START);
        builder.start(TEST_START);
        assertBuilderTime(builder, "start", TEST_START.toLocalTime());
    }
    @SuppressWarnings("ConstantValue")
    @Test(expected = IllegalArgumentException.class)
    public void t0109_builder_can_not_set_null_start_time() {
        LocalTime ldt = null;
        builder.start(ldt);
    }
    @Test
    public void t0110_builder_can_set_start_date_time() {
        assertBuilderDateTime(builder, "start", CalendarTools.MIN_DATE);
        builder.start(TEST_START);
        assertBuilderDateTime(builder, "start", TEST_START);
        assertBuilderTime(builder, "start", TEST_START.toLocalTime());
        assertBuilderDate(builder, "start", TEST_START.toLocalDate());
    }
    @SuppressWarnings("ConstantValue")
    @Test(expected = IllegalArgumentException.class)
    public void t0111_builder_can_not_set_null_start_date_time() {
        LocalDateTime ldt = null;
        builder.start(ldt);
    }
    @Test
    public void t0112_builder_can_set_end_time() {
        assertBuilderTime(builder, "end", CalendarTools.DAY_END);
        builder.end(TEST_END);
        assertBuilderTime(builder, "end", TEST_END.toLocalTime());
    }
    @SuppressWarnings("ConstantValue")
    @Test(expected = IllegalArgumentException.class)
    public void t0113_builder_can_not_set_null_end_time() {
        LocalTime ldt = null;
        builder.end(ldt);
    }
    @Test
    public void t0114_builder_can_set_end_date_time() {
        assertBuilderDateTime(builder, "end", CalendarTools.MAX_DATE);
        builder.end(TEST_END);
        assertBuilderDateTime(builder, "end", TEST_END);
        assertBuilderTime(builder, "end", TEST_END.toLocalTime());
        assertBuilderDate(builder, "end", TEST_END.toLocalDate());
    }
    @SuppressWarnings("ConstantValue")
    @Test(expected = IllegalArgumentException.class)
    public void t0115_builder_can_not_set_null_end_date_time() {
        LocalDateTime ldt = null;
        builder.end(ldt);
    }
    @Test(expected = IllegalArgumentException.class)
    public void t0116_builder_can_not_set_end_time_before_start_time() {
        LocalTime start = TEST_END.toLocalTime();
        LocalTime end = TEST_START.toLocalTime();
        LocalDateTime ldt = LocalDateTime.ofInstant(Instant.now(), TEST_TIMEZONE.toZoneId());
        builder.start(ldt).end(ldt).start(start).end(end);
    }
    @Test(expected = IllegalArgumentException.class)
    public void t0117_builder_can_not_set_start_time_after_end_time() {
        LocalTime start = TEST_END.toLocalTime();
        LocalTime end = TEST_START.toLocalTime();
        LocalDateTime ldt = LocalDateTime.ofInstant(Instant.now(), TEST_TIMEZONE.toZoneId());
        builder
            .start(ldt).end(ldt) // set same timestamp for start & end
            .end(end).start(start) // set start/end time
        ;
    }

    // endregion
    // region - 02: construct interval object

    @Test
    public void t0201_builder_can_create_default() {
        Interval interval = builder.build();
        assertNotNull(interval);
        assertUtcTimezone(interval);
        assertGivenDateTime(CalendarTools.MIN_DATE, interval.getStart());
        assertGivenDateTime(CalendarTools.MAX_DATE, interval.getEnd());
    }
    @Test
    public void t0202_builder_can_create_with_timezone() {
        Interval interval = builder
            .timezone(TEST_TIMEZONE)
            .build();
        assertNotNull(interval);
        assertGivenTimezone(interval, TEST_TIMEZONE);
        assertGivenDateTime(CalendarTools.MIN_DATE, interval.getStart());
        assertGivenDateTime(CalendarTools.MAX_DATE, interval.getEnd());
    }
    @Test
    public void t0203_builder_can_create_with_offset() {
        Interval interval = builder
            .offset(10)
            .start(TEST_START)
            .end(TEST_END)
            .build();
        assertNotNull(interval);
        assertUtcTimezone(interval);
        assertGivenDateTime(TEST_START.plusHours(10), interval.getStart());
        assertGivenDateTime(TEST_END.plusHours(10), interval.getEnd());
    }
    @Test
    public void t0203_builder_can_create_with_timezone_and_offset() {
        Interval interval = builder
            .timezone(TEST_TIMEZONE)
            .offset(10)
            .start(TEST_START)
            .end(TEST_END)
            .build();
        assertNotNull(interval);
        assertGivenTimezone(interval, TEST_TIMEZONE);
        assertGivenDateTime(TEST_START.plusHours(10), interval.getStart());
        assertGivenDateTime(TEST_END.plusHours(10), interval.getEnd());
    }

    // endregion
    // region - 03: construct custom intervals

    // region -> year

    @Test
    public void t0301_builder_can_create_year_interval() {
        Year year = IntervalBuilder.year(2023);
        assertNotNull(year);
        assertGivenDateTime(
            LocalDateTime.of(2023, 1, 1, 0, 0, 0, 0),
            year.getStart()
        );
        assertGivenDateTime(
            LocalDateTime.of(2023, 12, 31, 23, 59, 59, 999999999),
            year.getEnd()
        );
    }
    @Test
    public void t0302_builder_can_create_year_interval_with_timezone() {
        Year year = IntervalBuilder.year(2023, TEST_TIMEZONE_STR);
        assertNotNull(year);
        assertGivenDateTime(
                LocalDateTime.of(2023, 1, 1, 0, 0, 0, 0),
                year.getStart()
        );
        assertGivenDateTime(
                LocalDateTime.of(2023, 12, 31, 23, 59, 59, 999999999),
                year.getEnd()
        );
    }
    @Test
    public void t0303_builder_can_create_year_interval_with_offset() {
        Year year = IntervalBuilder.year(2023, 10);
        assertNotNull(year);
        assertGivenDateTime(
                LocalDateTime.of(2023, 1, 1, 10, 0, 0, 0),
                year.getStart()
        );
        assertGivenDateTime(
                LocalDateTime.of(2024, 1, 1, 9, 59, 59, 999999999),
                year.getEnd()
        );
    }
    @Test
    public void t0304_builder_can_create_year_interval_with_timezone_and_offset() {
        Year year = IntervalBuilder.year(2023, TEST_TIMEZONE_STR, 10);
        assertNotNull(year);
        assertGivenDateTime(
                LocalDateTime.of(2023, 1, 1, 10, 0, 0, 0),
                year.getStart()
        );
        assertGivenDateTime(
                LocalDateTime.of(2024, 1, 1, 9, 59, 59, 999999999),
                year.getEnd()
        );
    }

    // endregion
    // region -> month

    @Test
    public void t0305_builder_can_create_month_interval() {
        Month month = IntervalBuilder.month(2023, 10);
        assertNotNull(month);
        assertGivenDateTime(
            LocalDateTime.of(2023, 10, 1, 0, 0, 0, 0),
            month.getStart()
        );
        assertGivenDateTime(
            LocalDateTime.of(2023, 10, 31, 23, 59, 59, 999999999),
            month.getEnd()
        );
    }
    @Test
    public void t0306_builder_can_create_month_interval_with_timezone() {
        Month month = IntervalBuilder.month(2023, 10, TEST_TIMEZONE_STR);
        assertNotNull(month);
        assertGivenDateTime(
            LocalDateTime.of(2023, 10, 1, 0, 0, 0, 0),
            month.getStart()
        );
        assertGivenDateTime(
            LocalDateTime.of(2023, 10, 31, 23, 59, 59, 999999999),
            month.getEnd()
        );
    }
    @Test
    public void t0307_builder_can_create_month_interval_with_offset() {
        Month month = IntervalBuilder.month(2023, 10, 10);
        assertNotNull(month);
        assertGivenDateTime(
            LocalDateTime.of(2023, 10, 1, 10, 0, 0, 0),
            month.getStart()
        );
        assertGivenDateTime(
            LocalDateTime.of(2023, 11, 1, 9, 59, 59, 999999999),
            month.getEnd()
        );
    }
    @Test
    public void t0308_builder_can_create_month_interval_with_timezone_and_offset() {
        Month month = IntervalBuilder.month(2023, 10, TEST_TIMEZONE_STR, 10);
        assertNotNull(month);
        assertGivenDateTime(
            LocalDateTime.of(2023, 10, 1, 10, 0, 0, 0),
            month.getStart()
        );
        assertGivenDateTime(
            LocalDateTime.of(2023, 11, 1, 9, 59, 59, 999999999),
            month.getEnd()
        );
    }

    // endregion
    // region -> day

    @Test
    public void t0309_builder_can_create_day_interval() {
        Day day = IntervalBuilder.day(2023, 10, 15);
        assertNotNull(day);
        assertGivenDateTime(
            LocalDateTime.of(2023, 10, 15, 0, 0, 0, 0),
            day.getStart()
        );
        assertGivenDateTime(
            LocalDateTime.of(2023, 10, 15, 23, 59, 59, 999999999),
            day.getEnd()
        );
    }
    @Test
    public void t0310_builder_can_create_day_interval_with_timezone() {
        Day day = IntervalBuilder.day(2023, 10, 15, TEST_TIMEZONE_STR);
        assertNotNull(day);
        assertGivenDateTime(
            LocalDateTime.of(2023, 10, 15, 0, 0, 0, 0),
            day.getStart()
        );
        assertGivenDateTime(
            LocalDateTime.of(2023, 10, 15, 23, 59, 59, 999999999),
            day.getEnd()
        );
    }
    @Test
    public void t0311_builder_can_create_day_interval_with_offset() {
        Day day = IntervalBuilder.day(2023, 10, 15, 10);
        assertNotNull(day);
        assertGivenDateTime(
            LocalDateTime.of(2023, 10, 15, 10, 0, 0, 0),
            day.getStart()
        );
        assertGivenDateTime(
            LocalDateTime.of(2023, 10, 16, 9, 59, 59, 999999999),
            day.getEnd()
        );
    }
    @Test
    public void t0312_builder_can_create_day_interval_with_timezone_and_offset() {
        Day day = IntervalBuilder.day(2023, 10, 15, TEST_TIMEZONE_STR, 10);
        assertNotNull(day);
        assertGivenDateTime(
            LocalDateTime.of(2023, 10, 15, 10, 0, 0, 0),
            day.getStart()
        );
        assertGivenDateTime(
            LocalDateTime.of(2023, 10, 16, 9, 59, 59, 999999999),
            day.getEnd()
        );
    }

    // endregion
    // region -> errors

    @Test(expected = InvalidYearException.class)
    public void t0313_builder_can_not_create_invalid_month_interval() {
        IntervalBuilder.year(1999999999);
    }
    @Test(expected = InvalidMonthException.class)
    public void t0314_builder_can_not_create_invalid_month_interval() {
        IntervalBuilder.month(2023, -1);
    }
    @Test(expected = InvalidDayException.class)
    public void t0315_builder_can_not_create_invalid_day_interval() {
        IntervalBuilder.day(2023, -1, -1);
    }

    // endregion

    // endregion
    // region - 04: parse interval string

    // region -> year

    @Test
    public void t0401_builder_can_parse_interval_year() {
        Interval interval = IntervalBuilder.parse("2023");
        assertNotNull(interval);
        assertTrue(interval instanceof Year);
        assertUtcTimezone(interval);
        assertGivenDateTime(
            LocalDateTime.of(2023, 1, 1, 0, 0, 0, 0),
            interval.getStart())
        ;
        assertGivenDateTime(
            LocalDateTime.of(2023, 12, 31, 23, 59, 59, 999999999),
            interval.getEnd()
        );
    }
    @Test
    public void t0402_builder_can_parse_interval_year_with_timezone() {
        Interval interval = IntervalBuilder.parse("2023", TEST_TIMEZONE_STR);
        assertNotNull(interval);
        assertTrue(interval instanceof Year);
        assertGivenTimezone(interval, TEST_TIMEZONE);
        assertGivenDateTime(
            LocalDateTime.of(2023, 1, 1, 0, 0, 0, 0),
            interval.getStart())
        ;
        assertGivenDateTime(
            LocalDateTime.of(2023, 12, 31, 23, 59, 59, 999999999),
            interval.getEnd()
        );
    }
    @Test
    public void t0403_builder_can_parse_interval_year_with_offset() {
        Interval interval = IntervalBuilder.parse("2023", 10);
        assertNotNull(interval);
        assertTrue(interval instanceof Year);
        assertUtcTimezone(interval);
        assertGivenDateTime(
                LocalDateTime.of(2023, 1, 1, 10, 0, 0, 0),
                interval.getStart())
        ;
        assertGivenDateTime(
                LocalDateTime.of(2024, 1, 1, 9, 59, 59, 999999999),
                interval.getEnd()
        );
    }
    @Test
    public void t0404_builder_can_parse_interval_year_with_timezone_and_offset() {
        Interval interval = IntervalBuilder.parse("2023", TEST_TIMEZONE_STR, 10);
        assertNotNull(interval);
        assertTrue(interval instanceof Year);
        assertGivenTimezone(interval, TEST_TIMEZONE);
        assertGivenDateTime(
            LocalDateTime.of(2023, 1, 1, 10, 0, 0, 0),
            interval.getStart())
        ;
        assertGivenDateTime(
            LocalDateTime.of(2024, 1, 1, 9, 59, 59, 999999999),
            interval.getEnd()
        );
    }

    // endregion
    // region -> month
    @Test
    public void t0405_builder_can_parse_interval_month() {
        Interval interval = IntervalBuilder.parse("2023-10");
        assertNotNull(interval);
        assertTrue(interval instanceof Month);
        assertUtcTimezone(interval);
        assertGivenDateTime(
            LocalDateTime.of(2023, 10, 1, 0, 0, 0, 0),
            interval.getStart())
        ;
        assertGivenDateTime(
            LocalDateTime.of(2023, 10, 31, 23, 59, 59, 999999999),
            interval.getEnd()
        );
    }
    @Test
    public void t0406_builder_can_parse_interval_month_with_timezone() {
        Interval interval = IntervalBuilder.parse("2023-10", TEST_TIMEZONE_STR);
        assertNotNull(interval);
        assertTrue(interval instanceof Month);
        assertGivenTimezone(interval, TEST_TIMEZONE);
        assertGivenDateTime(
            LocalDateTime.of(2023, 10, 1, 0, 0, 0, 0),
            interval.getStart())
        ;
        assertGivenDateTime(
            LocalDateTime.of(2023, 10, 31, 23, 59, 59, 999999999),
            interval.getEnd()
        );
    }
    @Test
    public void t0407_builder_can_parse_interval_month_with_offset() {
        Interval interval = IntervalBuilder.parse("2023-10", 10);
        assertNotNull(interval);
        assertTrue(interval instanceof Month);
        assertUtcTimezone(interval);
        assertGivenDateTime(
                LocalDateTime.of(2023, 10, 1, 10, 0, 0, 0),
                interval.getStart())
        ;
        assertGivenDateTime(
                LocalDateTime.of(2023, 11, 1, 9, 59, 59, 999999999),
                interval.getEnd()
        );
    }
    @Test
    public void t0408_builder_can_parse_interval_month_with_timezone_and_offset() {
        Interval interval = IntervalBuilder.parse("2023-10", TEST_TIMEZONE_STR, 10);
        assertNotNull(interval);
        assertTrue(interval instanceof Month);
        assertGivenTimezone(interval, TEST_TIMEZONE);
        assertGivenDateTime(
            LocalDateTime.of(2023, 10, 1, 10, 0, 0, 0),
            interval.getStart())
        ;
        assertGivenDateTime(
            LocalDateTime.of(2023, 11, 1, 9, 59, 59, 999999999),
            interval.getEnd()
        );
    }
    // endregion
    // region -> day

    @Test
    public void t0409_builder_can_parse_interval_day() {
        Interval interval = IntervalBuilder.parse("2023-10-15");
        assertNotNull(interval);
        assertTrue(interval instanceof Day);
        assertUtcTimezone(interval);
        assertGivenDateTime(
            LocalDateTime.of(2023, 10, 15, 0, 0, 0, 0),
            interval.getStart())
        ;
        assertGivenDateTime(
            LocalDateTime.of(2023, 10, 15, 23, 59, 59, 999999999),
            interval.getEnd()
        );
    }
    @Test
    public void t0410_builder_can_parse_interval_day_with_timezone() {
        Interval interval = IntervalBuilder.parse("2023-10-15", TEST_TIMEZONE_STR);
        assertNotNull(interval);
        assertTrue(interval instanceof Day);
        assertGivenTimezone(interval, TEST_TIMEZONE);
        assertGivenDateTime(
            LocalDateTime.of(2023, 10, 15, 0, 0, 0, 0),
            interval.getStart())
        ;
        assertGivenDateTime(
            LocalDateTime.of(2023, 10, 15, 23, 59, 59, 999999999),
            interval.getEnd()
        );
    }
    @Test
    public void t0411_builder_can_parse_interval_day_with_offset() {
        Interval interval = IntervalBuilder.parse("2023-10-15", 10);
        assertNotNull(interval);
        assertTrue(interval instanceof Day);
        assertUtcTimezone(interval);
        assertGivenDateTime(
            LocalDateTime.of(2023, 10, 15, 10, 0, 0, 0),
            interval.getStart())
        ;
        assertGivenDateTime(
            LocalDateTime.of(2023, 10, 16, 9, 59, 59, 999999999),
            interval.getEnd()
        );
    }
    @Test
    public void t0412_builder_can_parse_interval_day_with_timezone_and_offset() {
        Interval interval = IntervalBuilder.parse("2023-10-15", TEST_TIMEZONE_STR, 10);
        assertNotNull(interval);
        assertTrue(interval instanceof Day);
        assertGivenTimezone(interval, TEST_TIMEZONE);
        assertGivenDateTime(
                LocalDateTime.of(2023, 10, 15, 10, 0, 0, 0),
                interval.getStart())
        ;
        assertGivenDateTime(
                LocalDateTime.of(2023, 10, 16, 9, 59, 59, 999999999),
                interval.getEnd()
        );
    }
    // endregion

    // endregion
    // region - 05: parse custom intervals

    // region -> year

    @Test
    public void t0501_can_create_year_from_string() {
        Year year = Year.of("2023");
        assertNotNull(year);
        assertUtcTimezone(year);
        assertGivenDateTime(
            LocalDateTime.of(2023, 1, 1, 0, 0, 0, 0),
            year.getStart())
        ;
        assertGivenDateTime(
            LocalDateTime.of(2023, 12, 31, 23, 59, 59, 999999999),
            year.getEnd()
        );
    }
    @Test
    public void t0502_can_create_year_from_string_with_timezone() {
        Year year = Year.of("2023", TEST_TIMEZONE_STR);
        assertNotNull(year);
        assertGivenTimezone(year, TEST_TIMEZONE);
        assertGivenDateTime(
            LocalDateTime.of(2023, 1, 1, 0, 0, 0, 0),
            year.getStart())
        ;
        assertGivenDateTime(
            LocalDateTime.of(2023, 12, 31, 23, 59, 59, 999999999),
            year.getEnd()
        );
    }
    @Test
    public void t0503_can_create_year_from_string_with_offset() {
        Year year = Year.of("2023", 10);
        assertNotNull(year);
        assertUtcTimezone(year);
        assertGivenDateTime(
            LocalDateTime.of(2023, 1, 1, 10, 0, 0, 0),
            year.getStart())
        ;
        assertGivenDateTime(
            LocalDateTime.of(2024, 1, 1, 9, 59, 59, 999999999),
            year.getEnd()
        );
    }
    @Test
    public void t0504_can_create_year_from_string_with_timezone_and_offset() {
        Year year = Year.of("2023", TEST_TIMEZONE_STR, 10);
        assertNotNull(year);
        assertGivenTimezone(year, TEST_TIMEZONE);
        assertGivenDateTime(
            LocalDateTime.of(2023, 1, 1, 10, 0, 0, 0),
            year.getStart())
        ;
        assertGivenDateTime(
            LocalDateTime.of(2024, 1, 1, 9, 59, 59, 999999999),
            year.getEnd()
        );
    }

    // endregion
    // region -> month
    @Test
    public void t0505_can_create_month_from_string() {
        Month month = Month.of("2023-10");
        assertNotNull(month);
        assertUtcTimezone(month);
        assertGivenDateTime(
            LocalDateTime.of(2023, 10, 1, 0, 0, 0, 0),
            month.getStart())
        ;
        assertGivenDateTime(
            LocalDateTime.of(2023, 10, 31, 23, 59, 59, 999999999),
            month.getEnd()
        );
    }
    @Test
    public void t0506_can_create_month_from_string_with_timezone() {
        Month month = Month.of("2023-10", TEST_TIMEZONE_STR);
        assertNotNull(month);
        assertGivenTimezone(month, TEST_TIMEZONE);
        assertGivenDateTime(
            LocalDateTime.of(2023, 10, 1, 0, 0, 0, 0),
            month.getStart())
        ;
        assertGivenDateTime(
            LocalDateTime.of(2023, 10, 31, 23, 59, 59, 999999999),
            month.getEnd()
        );
    }
    @Test
    public void t0507_can_create_month_from_string_with_offset() {
        Month month = Month.of("2023-10", 10);
        assertNotNull(month);
        assertUtcTimezone(month);
        assertGivenDateTime(
            LocalDateTime.of(2023, 10, 1, 10, 0, 0, 0),
            month.getStart())
        ;
        assertGivenDateTime(
            LocalDateTime.of(2023, 11, 1, 9, 59, 59, 999999999),
            month.getEnd()
        );
    }
    @Test
    public void t0508_can_create_month_from_string_with_timezone_and_offset() {
        Month month = Month.of("2023-10", TEST_TIMEZONE_STR, 10);
        assertNotNull(month);
        assertGivenTimezone(month, TEST_TIMEZONE);
        assertGivenDateTime(
            LocalDateTime.of(2023, 10, 1, 10, 0, 0, 0),
            month.getStart())
        ;
        assertGivenDateTime(
            LocalDateTime.of(2023, 11, 1, 9, 59, 59, 999999999),
            month.getEnd()
        );
    }
    // endregion
    // region -> day

    @Test
    public void t0509_can_create_day_from_string() {
        Day day = Day.of("2023-10-15");
        assertNotNull(day);
        assertUtcTimezone(day);
        assertGivenDateTime(
            LocalDateTime.of(2023, 10, 15, 0, 0, 0, 0),
            day.getStart())
        ;
        assertGivenDateTime(
            LocalDateTime.of(2023, 10, 15, 23, 59, 59, 999999999),
            day.getEnd()
        );
    }
    @Test
    public void t0510_can_create_day_from_string_with_timezone() {
        Day day = Day.of("2023-10-15", TEST_TIMEZONE_STR);
        assertNotNull(day);
        assertGivenTimezone(day, TEST_TIMEZONE);
        assertGivenDateTime(
            LocalDateTime.of(2023, 10, 15, 0, 0, 0, 0),
            day.getStart())
        ;
        assertGivenDateTime(
            LocalDateTime.of(2023, 10, 15, 23, 59, 59, 999999999),
            day.getEnd()
        );
    }
    @Test
    public void t0511_can_create_day_from_string_with_offset() {
        Day day = Day.of("2023-10-15", 10);
        assertNotNull(day);
        assertUtcTimezone(day);
        assertGivenDateTime(
            LocalDateTime.of(2023, 10, 15, 10, 0, 0, 0),
            day.getStart())
        ;
        assertGivenDateTime(
            LocalDateTime.of(2023, 10, 16, 9, 59, 59, 999999999),
            day.getEnd()
        );
    }
    @Test
    public void t0512_can_create_day_from_string_with_timezone_and_offset() {
        Day day = Day.of("2023-10-15", TEST_TIMEZONE_STR, 10);
        assertNotNull(day);
        assertGivenTimezone(day, TEST_TIMEZONE);
        assertGivenDateTime(
            LocalDateTime.of(2023, 10, 15, 10, 0, 0, 0),
            day.getStart())
        ;
        assertGivenDateTime(
            LocalDateTime.of(2023, 10, 16, 9, 59, 59, 999999999),
            day.getEnd()
        );
    }
    // endregion

    // endregion
    // region - 06: parse errors

    @Test(expected = InvalidIntervalFormatException.class)
    public void t0601_parse_fails_on_invalid_input() {
        IntervalBuilder.parse("2023-51");
    }
    @Test(expected = InvalidIntervalFormatException.class)
    public void t0602_parse_fails_on_invalid_input() {
        IntervalBuilder.parse("2023-10-");
    }
    @Test(expected = InvalidIntervalFormatException.class)
    public void t0603_parse_fails_on_invalid_input() {
        IntervalBuilder.parse("2023-");
    }
    @Test(expected = InvalidIntervalFormatException.class)
    public void t0604_parse_fails_on_invalid_input() {
        IntervalBuilder.parse("2023-10-51");
    }
    @Test(expected = InvalidIntervalFormatException.class)
    public void t0605_parse_fails_on_invalid_input() {
        IntervalBuilder.parse("2023-10-15-");
    }

    // endregion
    // region - 07: negative cases

    @Test(expected = IllegalArgumentException.class)
    public void t0701_fails_to_set_interval_start_after_end() {
        new IntervalBuilder().end(TEST_START).start(TEST_END);
    }
    @Test(expected = IllegalArgumentException.class)
    public void t0702_fails_to_set_interval_end_before_start() {
        new IntervalBuilder().start(TEST_END).end(TEST_START);
    }

    // endregion
    // region - 08: misc

    @Test
    public void t0801_can_reset_start_time() {
        Interval interval = new IntervalBuilder()
            .start(TEST_START)
            .start(LocalTime.of(12, 10, 15, 0))
            .build();
        assertEquals(TEST_START.getYear(), interval.getStart().getYear());
        assertEquals(TEST_START.getMonth(), interval.getStart().getMonth());
        assertEquals(TEST_START.getDayOfMonth(), interval.getStart().getDayOfMonth());
        assertEquals(12, interval.getStart().getHour());
        assertEquals(10, interval.getStart().getMinute());
        assertEquals(15, interval.getStart().getSecond());
    }

    @Test
    public void t0802_can_reset_end_time() {
        Interval interval = new IntervalBuilder()
            .end(TEST_END)
            .end(LocalTime.of(12, 10, 15, 0))
            .build();
        assertEquals(TEST_END.getYear(), interval.getEnd().getYear());
        assertEquals(TEST_END.getMonth(), interval.getEnd().getMonth());
        assertEquals(TEST_END.getDayOfMonth(), interval.getEnd().getDayOfMonth());
        assertEquals(12, interval.getEnd().getHour());
        assertEquals(10, interval.getEnd().getMinute());
        assertEquals(15, interval.getEnd().getSecond());
    }

    // endregion

}
