package ws.slink.intervals.test;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import ws.slink.intervals.Day;
import ws.slink.intervals.Interval;
import ws.slink.intervals.IntervalBuilder;
import ws.slink.intervals.Month;
import ws.slink.intervals.Year;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static ws.slink.intervals.test.common.Assertions.assertGivenDateTime;
import static ws.slink.intervals.test.common.Assertions.assertGivenTimezone;
import static ws.slink.intervals.test.common.TestConfig.TEST_END;
import static ws.slink.intervals.test.common.TestConfig.TEST_END_DAY_STR;
import static ws.slink.intervals.test.common.TestConfig.TEST_END_MONTH_STR;
import static ws.slink.intervals.test.common.TestConfig.TEST_END_YEAR_STR;
import static ws.slink.intervals.test.common.TestConfig.TEST_START;
import static ws.slink.intervals.test.common.TestConfig.TEST_START_DAY_STR;
import static ws.slink.intervals.test.common.TestConfig.TEST_START_MONTH_STR;
import static ws.slink.intervals.test.common.TestConfig.TEST_START_YEAR_STR;
import static ws.slink.intervals.test.common.TestConfig.TEST_TIMEZONE;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CustomIntervalTest {

    @Test
    public void t0101_builder_can_create_from_day_strings() {
        Interval interval = new IntervalBuilder()
            .timezone(TEST_TIMEZONE)
            .start(TEST_START_DAY_STR)
            .end(TEST_END_DAY_STR)
            .build();
        assertNotNull(interval);
        assertGivenTimezone(interval, TEST_TIMEZONE);

        Day dayStart = Day.of(TEST_START_DAY_STR);
        Day dayEnd = Day.of(TEST_END_DAY_STR);

        assertEquals(dayStart.getStart(), interval.getStart());
        assertEquals(dayEnd.getEnd(), interval.getEnd());
    }

    @Test
    public void t0102_builder_can_create_from_month_strings() {
        Interval interval = new IntervalBuilder()
            .timezone(TEST_TIMEZONE)
            .start(TEST_START_MONTH_STR)
            .end(TEST_END_MONTH_STR)
            .build();
        assertNotNull(interval);
        assertGivenTimezone(interval, TEST_TIMEZONE);

        Month monthStart = Month.of(TEST_START_MONTH_STR);
        Month monthEnd = Month.of(TEST_END_MONTH_STR);

        assertEquals(monthStart.getStart(), interval.getStart());
        assertEquals(monthEnd.getEnd(), interval.getEnd());
    }

    @Test
    public void t0103_builder_can_create_from_year_strings() {
        Interval interval = new IntervalBuilder()
            .timezone(TEST_TIMEZONE)
            .start(TEST_START_YEAR_STR)
            .end(TEST_END_YEAR_STR)
            .build();
        assertNotNull(interval);
        assertGivenTimezone(interval, TEST_TIMEZONE);

        Year yearStart = Year.of(TEST_START_YEAR_STR);
        Year yearEnd = Year.of(TEST_END_YEAR_STR);

        assertEquals(yearStart.getStart(), interval.getStart());
        assertEquals(yearEnd.getEnd(), interval.getEnd());
    }

    @Test
    public void t0104_instant_test() {

        TimeZone tz = TimeZone.getTimeZone("GMT+3");
        String testDay = "2024-08-01";

        Interval interval = new IntervalBuilder()
            .timezone(tz)
            .start(testDay)
            .end(testDay)
            .build();

        // check that interval contains instant
        String testTime = testDay + " 10:15:00";
        Instant instant = LocalDateTime.parse(testTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).atZone(tz.toZoneId()).toInstant();
        assertTrue(interval.contains(instant));
    }

}
