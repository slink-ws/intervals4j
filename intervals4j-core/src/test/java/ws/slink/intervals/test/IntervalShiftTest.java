package ws.slink.intervals.test;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import ws.slink.intervals.Day;
import ws.slink.intervals.Interval;
import ws.slink.intervals.IntervalBuilder;
import ws.slink.intervals.Month;
import ws.slink.intervals.Year;

import java.time.LocalDateTime;

import static ws.slink.intervals.test.common.Assertions.assertGivenTimezone;
import static ws.slink.intervals.test.common.TestConfig.TEST_TIMEZONE;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IntervalShiftTest {

    // region - shift with no offset
    @Test
    public void test0101_year_shift_left() {
        Year year = Year.of("2023", TEST_TIMEZONE.getID(), 0);
        Interval interval = year.shifted("-P1D");
        assertGivenTimezone(year, TEST_TIMEZONE);
        Assert.assertEquals(
            LocalDateTime.of(2022, 12, 31, 0, 0, 0, 0),
            interval.getStart()
        );
        Assert.assertEquals(
            LocalDateTime.of(2023, 12, 30, 23, 59, 59, 999999999),
            interval.getEnd()
        );
    }
    @Test
    public void test0102_year_shift_right() {
        Year year = Year.of("2023", TEST_TIMEZONE.getID(), 0);
        Interval interval = year.shifted("P1D");
        assertGivenTimezone(year, TEST_TIMEZONE);
        Assert.assertEquals(
            LocalDateTime.of(2023, 1, 2, 0, 0, 0, 0),
            interval.getStart()
        );
        Assert.assertEquals(
            LocalDateTime.of(2024, 1, 1, 23, 59, 59, 999999999),
            interval.getEnd()
        );
    }
    @Test
    public void test0103_month_shift_left() {
        Month month = Month.of("2023-09", TEST_TIMEZONE.getID(), 0);
        Interval interval = month.shifted("-P1D");
        assertGivenTimezone(month, TEST_TIMEZONE);
        Assert.assertEquals(
            LocalDateTime.of(2023, 8, 31, 0, 0, 0, 0),
            interval.getStart()
        );
        Assert.assertEquals(
            LocalDateTime.of(2023, 9, 29, 23, 59, 59, 999999999),
            interval.getEnd()
        );
    }
    @Test
    public void test0104_month_shift_right() {
        Month month = Month.of("2023-09", TEST_TIMEZONE.getID(), 0);
        Interval interval = month.shifted("P1D");
        assertGivenTimezone(month, TEST_TIMEZONE);
        Assert.assertEquals(
            LocalDateTime.of(2023, 9, 2, 0, 0, 0, 0),
            interval.getStart()
        );
        Assert.assertEquals(
            LocalDateTime.of(2023, 10, 1, 23, 59, 59, 999999999),
            interval.getEnd()
        );
    }
    @Test
    public void test0105_day_shift_left() {
        Day day = Day.of("2023-09-01", TEST_TIMEZONE.getID(), 0);
        Interval interval = day.shifted("PT-1H");
        assertGivenTimezone(day, TEST_TIMEZONE);
        Assert.assertEquals(
            LocalDateTime.of(2023, 8, 31, 23, 0, 0, 0),
            interval.getStart()
        );
        Assert.assertEquals(
            LocalDateTime.of(2023, 9, 1, 22, 59, 59, 999999999),
            interval.getEnd()
        );
    }
    @Test
    public void test0106_day_shift_right() {
        Day day = Day.of("2023-09-01", TEST_TIMEZONE.getID(), 0);
        Interval interval = day.shifted("PT1H");
        assertGivenTimezone(day, TEST_TIMEZONE);
        Assert.assertEquals(
            LocalDateTime.of(2023, 9, 1, 1, 0, 0, 0),
            interval.getStart()
        );
        Assert.assertEquals(
            LocalDateTime.of(2023, 9, 2, 0, 59, 59, 999999999),
            interval.getEnd()
        );
    }
    @Test
    public void test0107_interval_shift_left_v1() {
        Interval interval = new IntervalBuilder()
            .start(LocalDateTime.of(2023, 10, 10, 13, 30, 0))
            .end(LocalDateTime.of(2023, 10, 10, 15, 0, 0))
            .timezone(TEST_TIMEZONE)
            .offset(0)
            .build();
        interval = interval.shifted("PT-1H");
        assertGivenTimezone(interval, TEST_TIMEZONE);
        Assert.assertEquals(
            LocalDateTime.of(2023, 10, 10, 12, 30, 0, 0),
            interval.getStart()
        );
        Assert.assertEquals(
            LocalDateTime.of(2023, 10, 10, 14, 0, 0, 0),
            interval.getEnd()
        );
    }
    @Test
    public void test0108_interval_shift_left_v2() {
        Interval interval = new IntervalBuilder()
                .start(LocalDateTime.of(2023, 10, 10, 13, 30, 0))
                .end(LocalDateTime.of(2023, 10, 10, 15, 0, 0))
                .timezone(TEST_TIMEZONE)
                .offset(0)
                .build();
        interval = interval.shifted("-PT1H");
        assertGivenTimezone(interval, TEST_TIMEZONE);
        Assert.assertEquals(
                LocalDateTime.of(2023, 10, 10, 12, 30, 0, 0),
                interval.getStart()
        );
        Assert.assertEquals(
                LocalDateTime.of(2023, 10, 10, 14, 0, 0, 0),
                interval.getEnd()
        );
    }
    @Test
    public void test0109_interval_shift_right() {
        Interval interval = new IntervalBuilder()
            .start(LocalDateTime.of(2023, 10, 10, 13, 30, 0))
            .end(LocalDateTime.of(2023, 10, 10, 15, 0, 0))
            .timezone(TEST_TIMEZONE)
            .offset(0)
            .build();
        interval = interval.shifted("PT1H");
        assertGivenTimezone(interval, TEST_TIMEZONE);
        Assert.assertEquals(
            LocalDateTime.of(2023, 10, 10, 14, 30, 0, 0),
            interval.getStart()
        );
        Assert.assertEquals(
            LocalDateTime.of(2023, 10, 10, 16, 0, 0, 0),
            interval.getEnd()
        );
    }
    // endregion
    // region - shift with offset
    @Test
    public void test0201_year_shift_left() {
        Year year = Year.of("2023", TEST_TIMEZONE.getID(), 10);
        Interval interval = year.shifted("-P1D");
        assertGivenTimezone(year, TEST_TIMEZONE);
        Assert.assertEquals(
            LocalDateTime.of(2022, 12, 31, 10, 0, 0, 0),
            interval.getStart()
        );
        Assert.assertEquals(
                LocalDateTime.of(2023, 12, 31, 9, 59, 59, 999999999),
                interval.getEnd()
        );
    }
    @Test
    public void test0202_year_shift_right() {
        Year year = Year.of("2023", TEST_TIMEZONE.getID(), 10);
        Interval interval = year.shifted("P1D");
        assertGivenTimezone(year, TEST_TIMEZONE);
        Assert.assertEquals(
                LocalDateTime.of(2023, 1, 2, 10, 0, 0, 0),
                interval.getStart()
        );
        Assert.assertEquals(
                LocalDateTime.of(2024, 1, 2, 9, 59, 59, 999999999),
                interval.getEnd()
        );
    }
    @Test
    public void test0203_month_shift_left() {
        Month month = Month.of("2023-09", TEST_TIMEZONE.getID(), 10);
        Interval interval = month.shifted("-P1D");
        assertGivenTimezone(month, TEST_TIMEZONE);
        Assert.assertEquals(
                LocalDateTime.of(2023, 8, 31, 10, 0, 0, 0),
                interval.getStart()
        );
        Assert.assertEquals(
                LocalDateTime.of(2023, 9, 30, 9, 59, 59, 999999999),
                interval.getEnd()
        );
    }
    @Test
    public void test0204_month_shift_right() {
        Month month = Month.of("2023-09", TEST_TIMEZONE.getID(), 10);
        Interval interval = month.shifted("P1D");
        assertGivenTimezone(month, TEST_TIMEZONE);
        Assert.assertEquals(
                LocalDateTime.of(2023, 9, 2, 10, 0, 0, 0),
                interval.getStart()
        );
        Assert.assertEquals(
                LocalDateTime.of(2023, 10, 2, 9, 59, 59, 999999999),
                interval.getEnd()
        );
    }
    @Test
    public void test0205_day_shift_left() {
        Day day = Day.of("2023-09-01", TEST_TIMEZONE.getID(), 10);
        Interval interval = day.shifted("PT-1H");
        assertGivenTimezone(day, TEST_TIMEZONE);
        Assert.assertEquals(
                LocalDateTime.of(2023, 9, 1, 9, 0, 0, 0),
                interval.getStart()
        );
        Assert.assertEquals(
                LocalDateTime.of(2023, 9, 2, 8, 59, 59, 999999999),
                interval.getEnd()
        );
    }
    @Test
    public void test0206_day_shift_right() {
        Day day = Day.of("2023-09-01", TEST_TIMEZONE.getID(), 10);
        Interval interval = day.shifted("PT1H");
        assertGivenTimezone(day, TEST_TIMEZONE);
        Assert.assertEquals(
                LocalDateTime.of(2023, 9, 1, 11, 0, 0, 0),
                interval.getStart()
        );
        Assert.assertEquals(
                LocalDateTime.of(2023, 9, 2, 10, 59, 59, 999999999),
                interval.getEnd()
        );
    }
    @Test
    public void test0207_interval_shift_left_v1() {
        Interval interval = new IntervalBuilder()
                .start(LocalDateTime.of(2023, 10, 10, 13, 30, 0))
                .end(LocalDateTime.of(2023, 10, 10, 15, 0, 0))
                .timezone(TEST_TIMEZONE)
                .offset(10)
                .build();
        interval = interval.shifted("PT-1H");
        assertGivenTimezone(interval, TEST_TIMEZONE);
        Assert.assertEquals(
                LocalDateTime.of(2023, 10, 10, 22, 30, 0, 0),
                interval.getStart()
        );
        Assert.assertEquals(
                LocalDateTime.of(2023, 10, 11, 0, 0, 0, 0),
                interval.getEnd()
        );
    }
    @Test
    public void test0208_interval_shift_left_v2() {
        Interval interval = new IntervalBuilder()
                .start(LocalDateTime.of(2023, 10, 10, 13, 30, 0))
                .end(LocalDateTime.of(2023, 10, 10, 15, 0, 0))
                .timezone(TEST_TIMEZONE)
                .offset(10)
                .build();
        interval = interval.shifted("-PT1H");
        assertGivenTimezone(interval, TEST_TIMEZONE);
        Assert.assertEquals(
                LocalDateTime.of(2023, 10, 10, 22, 30, 0, 0),
                interval.getStart()
        );
        Assert.assertEquals(
                LocalDateTime.of(2023, 10, 11, 0, 0, 0, 0),
                interval.getEnd()
        );
    }
    @Test
    public void test0209_interval_shift_right() {
        Interval interval = new IntervalBuilder()
                .start(LocalDateTime.of(2023, 10, 10, 13, 30, 0))
                .end(LocalDateTime.of(2023, 10, 10, 15, 0, 0))
                .timezone(TEST_TIMEZONE)
                .offset(10)
                .build();
        interval = interval.shifted("PT1H");
        assertGivenTimezone(interval, TEST_TIMEZONE);
        Assert.assertEquals(
                LocalDateTime.of(2023, 10, 11, 0, 30, 0, 0),
                interval.getStart()
        );
        Assert.assertEquals(
                LocalDateTime.of(2023, 10, 11, 2, 0, 0, 0),
                interval.getEnd()
        );
    }
    // endregion

}
