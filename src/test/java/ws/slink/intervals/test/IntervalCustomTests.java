package ws.slink.intervals.test;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import ws.slink.intervals.Interval;
import ws.slink.intervals.IntervalBuilder;
import ws.slink.intervals.exception.MethodNotSupportedException;
import ws.slink.intervals.impl.CustomInterval;
import ws.slink.intervals.Day;
import ws.slink.intervals.Month;
import ws.slink.intervals.Year;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static ws.slink.intervals.test.common.Assertions.assertGivenTimezone;
import static ws.slink.intervals.test.common.TestConfig.TEST_END;
import static ws.slink.intervals.test.common.TestConfig.TEST_START;
import static ws.slink.intervals.test.common.TestConfig.TEST_TIMEZONE;
import static ws.slink.intervals.test.common.TestConfig.TEST_TIMEZONE_STR;
import static ws.slink.intervals.test.common.TestConfig.UTC_TIMEZONE;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IntervalCustomTests {

    // region - 00: fixed interval creation

    @Test
    public void test0001_create_year() {
        Year year = new Year(
                TEST_TIMEZONE,
                LocalDateTime.of(2023, 10, 1, 0, 0, 0, 0),
                LocalDateTime.of(2023, 10, 1, 0, 0, 0, 0)
        );
        assertGivenTimezone(year, TEST_TIMEZONE);
        assertEquals(
                LocalDateTime.of(2023, 1, 1, 0, 0, 0, 0),
                year.getStart()
        );
        assertEquals(
                LocalDateTime.of(2023, 12, 31, 23, 59, 59, 999999999),
                year.getEnd()
        );
    }
    @Test
    public void test0002_create_month() {
        Month month = new Month(
            TEST_TIMEZONE,
            LocalDateTime.of(2023, 10, 1, 0, 0, 0, 0),
            LocalDateTime.of(2023, 10, 1, 0, 0, 0, 0)
        );
        assertGivenTimezone(month, TEST_TIMEZONE);
        assertEquals(
            LocalDateTime.of(2023, 10, 1, 0, 0, 0, 0),
            month.getStart()
        );
        assertEquals(
            LocalDateTime.of(2023, 10, 31, 23, 59, 59, 999999999),
            month.getEnd()
        );
    }
    @Test
    public void test0003_create_day() {
        Day day = new Day(
            TEST_TIMEZONE,
            LocalDateTime.of(2023, 10, 15, 0, 0, 0, 0),
            LocalDateTime.of(2023, 10, 15, 0, 0, 0, 0)
        );
        assertGivenTimezone(day, TEST_TIMEZONE);
        assertEquals(
            LocalDateTime.of(2023, 10, 15, 0, 0, 0, 0),
            day.getStart()
        );
        assertEquals(
            LocalDateTime.of(2023, 10, 15, 23, 59, 59, 999999999),
            day.getEnd()
        );
    }

    // endregion
    // region - 01: previous year

    @Test
    public void test0101_check_previous_year() {
        Interval interval = IntervalBuilder.year(2023);
        Interval previous = interval.previous();
        assertEquals(UTC_TIMEZONE, previous.timezone());
        assertEquals(
            LocalDateTime.of(2022, 1, 1, 0, 0, 0, 0),
            previous.getStart()
        );
        assertEquals(
            LocalDateTime.of(2022, 12, 31, 23, 59, 59, 999999999),
            previous.getEnd()
        );
    }
    @Test
    public void test0102_check_previous_year_with_offset() {
        Interval interval = IntervalBuilder.year(2023, 10);
        Interval previous = interval.previous();
        assertEquals(UTC_TIMEZONE, previous.timezone());
        assertEquals(
            LocalDateTime.of(2022, 1, 1, 10, 0, 0, 0),
            previous.getStart()
        );
        assertEquals(
            LocalDateTime.of(2023, 1, 1, 9, 59, 59, 999999999),
            previous.getEnd()
        );
    }
    @Test
    public void test0103_check_previous_year_with_timezone() {
        Interval interval = IntervalBuilder.year(2023, TEST_TIMEZONE_STR);
        Interval previous = interval.previous();
        assertEquals(TEST_TIMEZONE, previous.timezone());
        assertEquals(
            LocalDateTime.of(2022, 1, 1, 0, 0, 0, 0),
            previous.getStart()
        );
        assertEquals(
            LocalDateTime.of(2022, 12, 31, 23, 59, 59, 999999999),
            previous.getEnd()
        );
    }
    @Test
    public void test0104_check_previous_year_with_timezone_and_offset() {
        Interval interval = IntervalBuilder.year(2023, TEST_TIMEZONE_STR, 10);
        Interval previous = interval.previous();
        assertEquals(TEST_TIMEZONE, previous.timezone());
        assertEquals(
                LocalDateTime.of(2022, 1, 1, 10, 0, 0, 0),
                previous.getStart()
        );
        assertEquals(
                LocalDateTime.of(2023, 1, 1, 9, 59, 59, 999999999),
                previous.getEnd()
        );
    }

    // endregion
    // region - 02: previous month

    @Test
    public void test0201_check_previous_month() {
        Interval interval = IntervalBuilder.month(2023, 11);
        Interval previous = interval.previous();
        assertEquals(UTC_TIMEZONE, previous.timezone());
        assertEquals(
            LocalDateTime.of(2023, 10, 1, 0, 0, 0, 0),
            previous.getStart()
        );
        assertEquals(
            LocalDateTime.of(2023, 10, 31, 23, 59, 59, 999999999),
            previous.getEnd()
        );
    }
    @Test
    public void test0202_check_previous_month_with_offset() {
        Interval interval = IntervalBuilder.month(2023, 11, 10);
        Interval previous = interval.previous();
        assertEquals(UTC_TIMEZONE, previous.timezone());
        assertEquals(
            LocalDateTime.of(2023, 10, 1, 10, 0, 0, 0),
            previous.getStart()
        );
        assertEquals(
            LocalDateTime.of(2023, 11, 1, 9, 59, 59, 999999999),
            previous.getEnd()
        );
    }
    @Test
    public void test0203_check_previous_month_with_timezone() {
        Interval interval = IntervalBuilder.month(2023, 11, TEST_TIMEZONE_STR);
        Interval previous = interval.previous();
        assertEquals(TEST_TIMEZONE, previous.timezone());
        assertEquals(
            LocalDateTime.of(2023, 10, 1, 0, 0, 0, 0),
            previous.getStart()
        );
        assertEquals(
            LocalDateTime.of(2023, 10, 31, 23, 59, 59, 999999999),
            previous.getEnd()
        );
    }
    @Test
    public void test0204_check_previous_month_with_timezone_and_offset() {
        Interval interval = IntervalBuilder.month(2023, 11, TEST_TIMEZONE_STR, 10);
        Interval previous = interval.previous();
        assertEquals(TEST_TIMEZONE, previous.timezone());
        assertEquals(
            LocalDateTime.of(2023, 10, 1, 10, 0, 0, 0),
            previous.getStart()
        );
        assertEquals(
            LocalDateTime.of(2023, 11, 1, 9, 59, 59, 999999999),
            previous.getEnd()
        );
    }

    // endregion
    // region - 03: previous day

    @Test
    public void test0301_check_previous_day() {
        Interval interval = IntervalBuilder.day(2023, 10, 15);
        Interval previous = interval.previous();
        assertEquals(UTC_TIMEZONE, previous.timezone());
        assertEquals(
            LocalDateTime.of(2023, 10, 14, 0, 0, 0, 0),
            previous.getStart()
        );
        assertEquals(
            LocalDateTime.of(2023, 10, 14, 23, 59, 59, 999999999),
            previous.getEnd()
        );
    }
    @Test
    public void test0302_check_previous_day_with_offset() {
        Interval interval = IntervalBuilder.day(2023, 10, 15, 10);
        Interval previous = interval.previous();
        assertEquals(UTC_TIMEZONE, previous.timezone());
        assertEquals(
                LocalDateTime.of(2023, 10, 14, 10, 0, 0, 0),
                previous.getStart()
        );
        assertEquals(
                LocalDateTime.of(2023, 10, 15, 9, 59, 59, 999999999),
                previous.getEnd()
        );
    }
    @Test
    public void test0303_check_previous_day_with_timezone() {
        Interval interval = IntervalBuilder.day(2023, 10, 15, TEST_TIMEZONE_STR);
        Interval previous = interval.previous();
        assertEquals(TEST_TIMEZONE, previous.timezone());
        assertEquals(
                LocalDateTime.of(2023, 10, 14, 0, 0, 0, 0),
                previous.getStart()
        );
        assertEquals(
                LocalDateTime.of(2023, 10, 14, 23, 59, 59, 999999999),
                previous.getEnd()
        );
    }
    @Test
    public void test0304_check_previous_day_with_timezone_and_offset() {
        Interval interval = IntervalBuilder.day(2023, 10, 15, TEST_TIMEZONE_STR, 10);
        Interval previous = interval.previous();
        assertEquals(TEST_TIMEZONE, previous.timezone());
        assertEquals(
                LocalDateTime.of(2023, 10, 14, 10, 0, 0, 0),
                previous.getStart()
        );
        assertEquals(
                LocalDateTime.of(2023, 10, 15, 9, 59, 59, 999999999),
                previous.getEnd()
        );
    }

    // endregion
    // region - 04: with previous year

    @Test
    public void test0401_check_with_previous_year() {
        Interval interval = IntervalBuilder.year(2023);
        Interval previous = interval.withPrevious();
        assertEquals(UTC_TIMEZONE, previous.timezone());
        assertEquals(
            LocalDateTime.of(2022, 1, 1, 0, 0, 0, 0),
            previous.getStart()
        );
        assertEquals(
            LocalDateTime.of(2023, 12, 31, 23, 59, 59, 999999999),
            previous.getEnd()
        );
    }
    @Test
    public void test0402_check_with_previous_year_with_offset() {
        Interval interval = IntervalBuilder.year(2023, 10);
        Interval previous = interval.withPrevious();
        assertEquals(UTC_TIMEZONE, previous.timezone());
        assertEquals(
            LocalDateTime.of(2023, 1, 1, 10, 0, 0, 0),
            interval.getStart()
        );
        assertEquals(
            LocalDateTime.of(2024, 1, 1, 9, 59, 59, 999999999),
            interval.getEnd()
        );
        assertEquals(
            LocalDateTime.of(2022, 1, 1, 10, 0, 0, 0),
            previous.getStart()
        );
        assertEquals(
            LocalDateTime.of(2024, 1, 1, 9, 59, 59, 999999999),
            previous.getEnd()
        );
    }
    @Test
    public void test0403_check_with_previous_year_with_timezone() {
        Interval interval = IntervalBuilder.year(2023, TEST_TIMEZONE_STR);
        Interval previous = interval.withPrevious();
        assertEquals(TEST_TIMEZONE, previous.timezone());
        assertEquals(
            LocalDateTime.of(2022, 1, 1, 0, 0, 0, 0),
            previous.getStart()
        );
        assertEquals(
            LocalDateTime.of(2023, 12, 31, 23, 59, 59, 999999999),
            previous.getEnd()
        );
    }
    @Test
    public void test0404_check_with_previous_year_with_timezone_and_offset() {
        Interval interval = IntervalBuilder.year(2023, TEST_TIMEZONE_STR, 10);
        Interval previous = interval.withPrevious();
        assertEquals(TEST_TIMEZONE, previous.timezone());
        assertEquals(
            LocalDateTime.of(2022, 1, 1, 10, 0, 0, 0),
            previous.getStart()
        );
        assertEquals(
            LocalDateTime.of(2024, 1, 1, 9, 59, 59, 999999999),
            previous.getEnd()
        );
    }

    // endregion
    // region - 05: with previous month

    @Test
    public void test0501_check_with_previous_month() {
        Interval interval = IntervalBuilder.month(2023, 10);
        Interval previous = interval.withPrevious();
        assertEquals(UTC_TIMEZONE, previous.timezone());
        assertEquals(
            LocalDateTime.of(2023, 9, 1, 0, 0, 0, 0),
            previous.getStart()
        );
        assertEquals(
            LocalDateTime.of(2023, 10, 31, 23, 59, 59, 999999999),
            previous.getEnd()
        );
    }
    @Test
    public void test0502_check_with_previous_month_with_offset() {
        Interval interval = IntervalBuilder.month(2023, 10, 10);
        Interval previous = interval.withPrevious();
        assertEquals(UTC_TIMEZONE, previous.timezone());
        assertEquals(
            LocalDateTime.of(2023, 9, 1, 10, 0, 0, 0),
            previous.getStart()
        );
        assertEquals(
            LocalDateTime.of(2023, 11, 1, 9, 59, 59, 999999999),
            previous.getEnd()
        );
    }
    @Test
    public void test0503_check_with_previous_month_with_timezone() {
        Interval interval = IntervalBuilder.month(2023, 10, TEST_TIMEZONE_STR);
        Interval previous = interval.withPrevious();
        assertEquals(TEST_TIMEZONE, previous.timezone());
        assertEquals(
            LocalDateTime.of(2023, 9, 1, 0, 0, 0, 0),
            previous.getStart()
        );
        assertEquals(
            LocalDateTime.of(2023, 10, 31, 23, 59, 59, 999999999),
            previous.getEnd()
        );
    }
    @Test
    public void test0504_check_with_previous_month_with_timezone_and_offset() {
        Interval interval = IntervalBuilder.month(2023, 10, TEST_TIMEZONE_STR, 10);
        Interval previous = interval.withPrevious();
        assertEquals(TEST_TIMEZONE, previous.timezone());
        assertEquals(
            LocalDateTime.of(2023, 9, 1, 10, 0, 0, 0),
            previous.getStart()
        );
        assertEquals(
            LocalDateTime.of(2023, 11, 1, 9, 59, 59, 999999999),
            previous.getEnd()
        );
    }

    // endregion
    // region - 06: with previous day

    @Test
    public void test0601_check_with_previous_day() {
        Interval interval = IntervalBuilder.day(2023, 10, 15);
        Interval previous = interval.withPrevious();
        assertEquals(UTC_TIMEZONE, previous.timezone());
        assertEquals(
            LocalDateTime.of(2023, 10, 14, 0, 0, 0, 0),
            previous.getStart()
        );
        assertEquals(
            LocalDateTime.of(2023, 10, 15, 23, 59, 59, 999999999),
            previous.getEnd()
        );
    }
    @Test
    public void test0602_check_with_previous_day_with_offset() {
        Interval interval = IntervalBuilder.day(2023, 10, 15, 10);
        Interval previous = interval.withPrevious();
        assertEquals(UTC_TIMEZONE, previous.timezone());
        assertEquals(
            LocalDateTime.of(2023, 10, 14, 10, 0, 0, 0),
            previous.getStart()
        );
        assertEquals(
            LocalDateTime.of(2023, 10, 16, 9, 59, 59, 999999999),
            previous.getEnd()
        );
    }
    @Test
    public void test0603_check_with_previous_day_with_timezone() {
        Interval interval = IntervalBuilder.day(2023, 10, 15, TEST_TIMEZONE_STR);
        Interval previous = interval.withPrevious();
        assertEquals(TEST_TIMEZONE, previous.timezone());
        assertEquals(
            LocalDateTime.of(2023, 10, 14, 0, 0, 0, 0),
            previous.getStart()
        );
        assertEquals(
            LocalDateTime.of(2023, 10, 15, 23, 59, 59, 999999999),
            previous.getEnd()
        );
    }
    @Test
    public void test0604_check_with_previous_day_with_timezone_and_offset() {
        Interval interval = IntervalBuilder.day(2023, 10, 15, TEST_TIMEZONE_STR, 10);
        Interval previous = interval.withPrevious();
        assertEquals(TEST_TIMEZONE, previous.timezone());
        assertEquals(
            LocalDateTime.of(2023, 10, 14, 10, 0, 0, 0),
            previous.getStart()
        );
        assertEquals(
            LocalDateTime.of(2023, 10, 16, 9, 59, 59, 999999999),
            previous.getEnd()
        );
    }

    // endregion
    // region - 07: corner cases

    @Test
    public void test0701_check_previous_month_for_january() {
        Interval interval = IntervalBuilder.month(2023, 1);
        Interval previous = interval.previous();
        assertEquals(UTC_TIMEZONE, previous.timezone());
        assertEquals(
            LocalDateTime.of(2022, 12, 1, 0, 0, 0, 0),
            previous.getStart()
        );
        assertEquals(
            LocalDateTime.of(2022, 12, 31, 23, 59, 59, 999999999),
            previous.getEnd()
        );
    }
    @Test
    public void test0702_check_previous_day_for_the_first_of_january() {
        Interval interval = IntervalBuilder.day(2023, 1, 1);
        Interval previous = interval.previous();
        assertEquals(UTC_TIMEZONE, previous.timezone());
        assertEquals(
            LocalDateTime.of(2022, 12, 31, 0, 0, 0, 0),
            previous.getStart()
        );
        assertEquals(
            LocalDateTime.of(2022, 12, 31, 23, 59, 59, 999999999),
            previous.getEnd()
        );
    }
    @Test
    public void test0703_check_previous_day_for_the_first_of_march_of_leap_year() {
        Interval interval = IntervalBuilder.day(2020, 3, 1);
        Interval previous = interval.previous();
        assertEquals(UTC_TIMEZONE, previous.timezone());
        assertEquals(
            LocalDateTime.of(2020, 2, 29, 0, 0, 0, 0),
            previous.getStart()
        );
        assertEquals(
            LocalDateTime.of(2020, 2, 29, 23, 59, 59, 999999999),
            previous.getEnd()
        );
    }

    @Test
    public void test0704_check_with_previous_month_for_january() {
        Interval interval = IntervalBuilder.month(2023, 1);
        Interval previous = interval.withPrevious();
        assertEquals(UTC_TIMEZONE, previous.timezone());
        assertEquals(
            LocalDateTime.of(2022, 12, 1, 0, 0, 0, 0),
            previous.getStart()
        );
        assertEquals(
            LocalDateTime.of(2023, 1, 31, 23, 59, 59, 999999999),
            previous.getEnd()
        );
    }
    @Test
    public void test0705_check_with_previous_day_for_the_first_of_january() {
        Interval interval = IntervalBuilder.day(2023, 1, 1);
        Interval previous = interval.withPrevious();
        assertEquals(UTC_TIMEZONE, previous.timezone());
        assertEquals(
                LocalDateTime.of(2022, 12, 31, 0, 0, 0, 0),
                previous.getStart()
        );
        assertEquals(
                LocalDateTime.of(2023, 1, 1, 23, 59, 59, 999999999),
                previous.getEnd()
        );
    }
    @Test
    public void test0706_check_with_previous_day_for_the_first_of_march_of_leap_year() {
        Interval interval = IntervalBuilder.day(2020, 3, 1);
        Interval previous = interval.withPrevious();
        assertEquals(UTC_TIMEZONE, previous.timezone());
        assertEquals(
                LocalDateTime.of(2020, 2, 29, 0, 0, 0, 0),
                previous.getStart()
        );
        assertEquals(
                LocalDateTime.of(2020, 3, 1, 23, 59, 59, 999999999),
                previous.getEnd()
        );
    }

    // endregion
    // region - 08: negative cases

    @Test(expected = MethodNotSupportedException.class)
    public void test0801_previous_fails_for_common_interval() {
        new IntervalBuilder().build().previous();
    }
    @Test(expected = MethodNotSupportedException.class)
    public void test0802_with_previous_fails_for_common_interval() {
        new IntervalBuilder().build().withPrevious();
    }

    @Test(expected = IllegalArgumentException.class)
    public void test0803_create_interval_with_null_timezone() {
        new CustomInterval(null, null, null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void test0804_create_interval_with_null_start() {
        new CustomInterval(TimeZone.getTimeZone(ZoneOffset.UTC), null, null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void test0805_create_interval_with_null_end() {
        new CustomInterval(
            TimeZone.getTimeZone(ZoneOffset.UTC),
            TEST_START,
            null
        );
    }
    @Test(expected = IllegalArgumentException.class)
    public void test0806_create_interval_with_invalid_dates() {
        new CustomInterval(
            TimeZone.getTimeZone(ZoneOffset.UTC),
            TEST_END,
            TEST_START
        );
    }
    @Test(expected = IllegalArgumentException.class)
    public void test0807_create_interval_with_invalid_offset() {
        new CustomInterval(
                TimeZone.getTimeZone(ZoneOffset.UTC),
                TEST_START,
                TEST_END,
                31
        );
    }

    // endregion
    // region - 09: misc

    @Test
    public void test0901_interval_to_string_test() {
        Interval interval = new CustomInterval(
            TimeZone.getTimeZone(ZoneOffset.UTC),
            TEST_START,
            TEST_END
        );
        assertEquals("2023-10-01 00:00:00.000 - 2023-10-31 23:59:59.999 UTC", interval.toString());
    }

    // endregion
}
