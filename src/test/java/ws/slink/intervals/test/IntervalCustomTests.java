package ws.slink.intervals.test;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import ws.slink.intervals.Interval;
import ws.slink.intervals.IntervalBuilder;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static ws.slink.intervals.test.common.TestConfig.TEST_TIMEZONE;
import static ws.slink.intervals.test.common.TestConfig.TEST_TIMEZONE_STR;
import static ws.slink.intervals.test.common.TestConfig.UTC_TIMEZONE;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IntervalCustomTests {

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
    public void test0102_check_previous_year_with_offet() {
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
    public void test0202_check_previous_month_with_offet() {
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

}
