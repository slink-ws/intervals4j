package ws.slink.intervals.test;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import ws.slink.intervals.exception.InvalidDayException;
import ws.slink.intervals.exception.InvalidMonthException;
import ws.slink.intervals.exception.InvalidYearException;
import ws.slink.intervals.impl.Day;
import ws.slink.intervals.impl.Month;
import ws.slink.intervals.impl.Year;
import ws.slink.intervals.tools.CalendarTools;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;

import static org.junit.Assert.assertEquals;
import static ws.slink.intervals.test.common.Assertions.assertGivenDateTime;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CalendarToolsTest {

    @Test
    public void test0101_start_of_day_temporal_adjuster_work() {
        ZonedDateTime zdt = Instant.now().atZone(ZoneOffset.UTC).with(CalendarTools.TO_START_OF_DAY);
        assertEquals("hour should be 0", 0, zdt.get(ChronoField.HOUR_OF_DAY));
        assertEquals("minute should be 0", 0, zdt.get(ChronoField.MINUTE_OF_HOUR));
        assertEquals("second should be 0", 0, zdt.get(ChronoField.SECOND_OF_MINUTE));
        assertEquals("milli should be 0", 0, zdt.get(ChronoField.MILLI_OF_SECOND));
        assertEquals("nano should be 0", 0, zdt.get(ChronoField.NANO_OF_SECOND));
    }

    @Test
    public void test0102_end_of_day_temporal_adjuster_work() {
        ZonedDateTime zdt = Instant.now().atZone(ZoneOffset.UTC).with(CalendarTools.TO_END_OF_DAY);
        assertEquals("hour should be 23", 23, zdt.get(ChronoField.HOUR_OF_DAY));
        assertEquals("minute should be 59", 59, zdt.get(ChronoField.MINUTE_OF_HOUR));
        assertEquals("second should be 59", 59, zdt.get(ChronoField.SECOND_OF_MINUTE));
        assertEquals("milli should be 999", 999, zdt.get(ChronoField.MILLI_OF_SECOND));
        assertEquals("nano should be 999999999", 999999999, zdt.get(ChronoField.NANO_OF_SECOND));
    }


    @Test
    public void test0201_create_year_from_string() {
        Year year = (Year)CalendarTools.yearFromString("2023");
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
    public void test0202_create_year_from_string_with_offset() {
        Year year = (Year)CalendarTools.yearFromString("2023", 10);
        assertGivenDateTime(
                LocalDateTime.of(2023, 1, 1, 10, 0, 0, 0),
                year.getStart()
        );
        assertGivenDateTime(
                LocalDateTime.of(2024, 1, 1, 9, 59, 59, 999999999),
                year.getEnd()
        );
    }
    @Test(expected = InvalidYearException.class)
    public void test0203_fail_on_invalid_year() {
        CalendarTools.yearFromString("2023-");
    }


    @Test
    public void test0204_create_month_from_string() {
        Month month = (Month)CalendarTools.monthFromString("2023-10");
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
    public void test0205_create_month_from_string_with_offset() {
        Month month = (Month)CalendarTools.monthFromString("2023-10", 10);
        assertGivenDateTime(
            LocalDateTime.of(2023, 10, 1, 10, 0, 0, 0),
            month.getStart()
        );
        assertGivenDateTime(
            LocalDateTime.of(2023, 11, 1, 9, 59, 59, 999999999),
            month.getEnd()
        );
    }
    @Test(expected = InvalidMonthException.class)
    public void test0206_fail_on_invalid_month() {
        CalendarTools.monthFromString("2023-21");
    }

    @Test
    public void test0207_create_day_from_string() {
        Day day = (Day)CalendarTools.dayFromString("2023-10-15");
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
    public void test0208_create_day_from_string_with_offset() {
        Day day = (Day)CalendarTools.dayFromString("2023-10-15", 10);
        assertGivenDateTime(
            LocalDateTime.of(2023, 10, 15, 10, 0, 0, 0),
            day.getStart()
        );
        assertGivenDateTime(
            LocalDateTime.of(2023, 10, 16, 9, 59, 59, 999999999),
            day.getEnd()
        );
    }
    @Test(expected = InvalidDayException.class)
    public void test0209_fail_on_invalid_day() {
        CalendarTools.dayFromString("2023-10-34");
    }

}
