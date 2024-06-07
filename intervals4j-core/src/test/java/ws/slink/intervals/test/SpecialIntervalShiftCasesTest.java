package ws.slink.intervals.test;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import ws.slink.intervals.Day;
import ws.slink.intervals.Interval;
import ws.slink.intervals.IntervalBuilder;
import ws.slink.intervals.Month;
import ws.slink.intervals.ShiftedDay;
import ws.slink.intervals.ShiftedInterval;
import ws.slink.intervals.ShiftedMonth;
import ws.slink.intervals.ShiftedYear;
import ws.slink.intervals.Year;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SpecialIntervalShiftCasesTest {

    @Test
    public void test3_edge_case_shifted_interval_end() {
        Interval interval = ShiftedInterval.of(IntervalBuilder.parse("2024-02-06", "CET"), "PT-2H+0.001000001S");
        ZonedDateTime zdt = ZonedDateTime.of(
                2024,
                02,
                06,
                21,
                00,
                0,
                1000000,
                ZoneId.of("UTC")
        );
        System.out.println("interval end  : " + interval.end());
        System.out.println("test timestamp: " + zdt.toInstant());
        assertThat("interval should contain timestamp", interval.contains(zdt));
    }
    @Test
    public void test4_edge_case_shifted_interval_end() {
        Interval interval = ShiftedInterval.of(IntervalBuilder.parse("2024-02-06", "CET"), "PT-2H+0.001000001S");
        ZonedDateTime zdt = ZonedDateTime.of(
                2024,
                2,
                6,
                21,
                0,
                0,
                1000000,
                ZoneId.of("UTC")
        );
        System.out.println("interval end  : " + interval.end());
        System.out.println("test timestamp: " + zdt.toInstant());
        assertThat("interval should not contain timestamp", not(interval.contains(zdt)));
    }
    @Test
    public void test5_edge_case_shifted_interval_end() {
        Interval interval = ShiftedInterval.of(IntervalBuilder.parse("2024-02-06", "CET"), "PT-2H+0.001000001S");
        ZonedDateTime zdt = ZonedDateTime.of(
                2024,
                2,
                6,
                22,
                1,
                0,
                0,
                ZoneId.of("UTC")
        );
        System.out.println("interval end  : " + interval.end());
        System.out.println("test timestamp: " + zdt.toInstant());
        assertThat("interval should not contain timestamp", not(interval.contains(zdt)));
    }

    @Test
    public void test6_shifted_day_previous() {
        Day month = Day.of("2023-11-30");
        ShiftedDay shiftedDay = ShiftedDay.of(month, "PT-1H");
        Interval previous = shiftedDay.previous();
        System.out.println("interval start  : " + previous.start());
        System.out.println("interval end: " + previous.end());
        Assert.assertEquals(
            LocalDateTime.of(2023, 11, 28, 23, 0, 0, 0),
            previous.getStart()
        );
        Assert.assertEquals(
            LocalDateTime.of(2023, 11, 29, 22, 59, 59, 999999999),
            previous.getEnd()
        );
    }
    @Test
    public void test7_shifted_day_with_previous() {
        Day month = Day.of("2023-11-30");
        ShiftedDay shiftedDay = ShiftedDay.of(month, "PT-1H");
        Interval previous = shiftedDay.withPrevious();
        System.out.println("interval start  : " + previous.start());
        System.out.println("interval end: " + previous.end());
        Assert.assertEquals(
            LocalDateTime.of(2023, 11, 28, 23, 0, 0, 0),
            previous.getStart()
        );
        Assert.assertEquals(
            LocalDateTime.of(2023, 11, 30, 22, 59, 59, 999999999),
            previous.getEnd()
        );
    }
    @Test
    public void test8_shifted_month_previous() {
        Month month = Month.of("2023-11");
        ShiftedMonth shiftedMonth = ShiftedMonth.of(month, "PT-1H");
        Interval previous = shiftedMonth.previous();
        System.out.println("interval start  : " + previous.start());
        System.out.println("interval end: " + previous.end());
        Assert.assertEquals(
            LocalDateTime.of(2023, 9, 30, 23, 0, 0, 0),
            previous.getStart()
        );
        Assert.assertEquals(
            LocalDateTime.of(2023, 10, 31, 22, 59, 59, 999999999),
            previous.getEnd()
        );
    }
    @Test
    public void test9_shifted_month_with_previous() {
        Month month = Month.of("2023-11");
        ShiftedMonth shiftedMonth = ShiftedMonth.of(month, "PT-1H");
        Interval previous = shiftedMonth.withPrevious();
        System.out.println("interval start  : " + previous.start());
        System.out.println("interval end: " + previous.end());
        Assert.assertEquals(
            LocalDateTime.of(2023, 9, 30, 23, 0, 0, 0),
            previous.getStart()
        );
        Assert.assertEquals(
            LocalDateTime.of(2023, 11, 30, 22, 59, 59, 999999999),
            previous.getEnd()
        );
    }
    @Test
    public void test10_shifted_year_previous() {
        Year year = Year.of("2023");
        ShiftedYear shiftedYear = ShiftedYear.of(year, "PT-1H");
        Interval previous = shiftedYear.previous();
        System.out.println("interval start  : " + previous.start());
        System.out.println("interval end: " + previous.end());
        Assert.assertEquals(
            LocalDateTime.of(2021, 12, 31, 23, 0, 0, 0),
            previous.getStart()
        );
        Assert.assertEquals(
            LocalDateTime.of(2022, 12, 31, 22, 59, 59, 999999999),
            previous.getEnd()
        );
    }
    @Test
    public void test11_shifted_year_with_previous() {
        Year year = Year.of("2023");
        ShiftedYear shiftedYear = ShiftedYear.of(year, "PT-1H");
        Interval previous = shiftedYear.withPrevious();
        System.out.println("interval start  : " + previous.start());
        System.out.println("interval end: " + previous.end());
        Assert.assertEquals(
            LocalDateTime.of(2021, 12, 31, 23, 0, 0, 0),
            previous.getStart()
        );
        Assert.assertEquals(
            LocalDateTime.of(2023, 12, 31, 22, 59, 59, 999999999),
            previous.getEnd()
        );
    }

}
