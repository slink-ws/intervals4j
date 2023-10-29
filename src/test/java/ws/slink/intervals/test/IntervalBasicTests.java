package ws.slink.intervals.test;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import ws.slink.intervals.Interval;
import ws.slink.intervals.impl.CustomInterval;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.TimeZone;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static ws.slink.intervals.test.common.TestConfig.TEST_END;
import static ws.slink.intervals.test.common.TestConfig.TEST_START;
import static ws.slink.intervals.test.common.TestConfig.TEST_TIMEZONE;
import static ws.slink.intervals.test.common.Assertions.assertGivenTimezone;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IntervalBasicTests {

    // region - 01: creation

    @Test
    public void test0101_can_create_interval_with_no_offset() {
        Interval interval = new CustomInterval(TEST_TIMEZONE, TEST_START, TEST_END);
        assertEquals(TEST_TIMEZONE, interval.timezone());
        assertEquals(TEST_START.atZone(TEST_TIMEZONE.toZoneId()).toInstant(), interval.start());
        assertEquals(TEST_END.atZone(TEST_TIMEZONE.toZoneId()).toInstant(), interval.end());
    }
    @Test
    public void test0102_can_create_interval_with_zero_offset() {
        Interval interval = new CustomInterval(TEST_TIMEZONE, TEST_START, TEST_END, 0);
        assertGivenTimezone(interval, TEST_TIMEZONE);
        assertEquals(TEST_START.atZone(TEST_TIMEZONE.toZoneId()).toInstant(), interval.start());
        assertEquals(TEST_END.atZone(TEST_TIMEZONE.toZoneId()).toInstant(), interval.end());
    }
    @Test
    public void test0103_can_create_interval_with_positive_offset() {
        Interval interval = new CustomInterval(TEST_TIMEZONE, TEST_START, TEST_END, 10);
        assertGivenTimezone(interval, TEST_TIMEZONE);
        assertEquals(TEST_START.atZone(TEST_TIMEZONE.toZoneId()).plusHours(10).toInstant(), interval.start());
        assertEquals(TEST_END.atZone(TEST_TIMEZONE.toZoneId()).plusHours(10).toInstant(), interval.end());
    }
    @Test
    public void test0104_can_create_interval_with_negative_offset() {
        Interval interval = new CustomInterval(TEST_TIMEZONE, TEST_START, TEST_END, -10);
        assertGivenTimezone(interval, TEST_TIMEZONE);
        assertEquals(TEST_START.atZone(TEST_TIMEZONE.toZoneId()).plusHours(-10).toInstant(), interval.start());
        assertEquals(TEST_END.atZone(TEST_TIMEZONE.toZoneId()).plusHours(-10).toInstant(), interval.end());
    }

    // endregion
    // region - 02: checks

    @Test
    public void test0201_interval_contains_valid_timestamp() {
        Interval interval = new CustomInterval(TEST_TIMEZONE, TEST_START, TEST_END);
        LocalDateTime checkTime = TEST_START.plusHours(1);
        ZonedDateTime checkZdt = checkTime.atZone(TEST_TIMEZONE.toZoneId());
        Instant checkTs = checkZdt.toInstant();
        assertTrue("should contain valid timestamp", interval.contains(checkZdt));
        assertTrue("should contain valid timestamp", interval.contains(checkTs));
    }

    @Test
    public void test0202_interval_does_not_contain_invalid_timestamp() {
        Interval interval = new CustomInterval(TEST_TIMEZONE, TEST_START, TEST_END);
        LocalDateTime checkTime = TEST_END.plusDays(1);
        ZonedDateTime checkZdt = checkTime.atZone(TEST_TIMEZONE.toZoneId());
        Instant checkTs = checkZdt.toInstant();
        assertFalse("should not contain invalid timestamp", interval.contains(checkZdt));
        assertFalse("should not contain invalid timestamp", interval.contains(checkTs));
        checkTime = TEST_START.minusDays(1);
        checkZdt = checkTime.atZone(TEST_TIMEZONE.toZoneId());
        checkTs = checkZdt.toInstant();
        assertFalse("should not contain invalid timestamp", interval.contains(checkZdt));
        assertFalse("should not contain invalid timestamp", interval.contains(checkTs));
    }

    @Test
    public void test0203_interval_contains_interval_start() {
        Interval interval = new CustomInterval(TEST_TIMEZONE, TEST_START, TEST_END);
        ZonedDateTime checkZdt = TEST_START.atZone(TEST_TIMEZONE.toZoneId());
        Instant checkTs = checkZdt.toInstant();
        assertTrue("should contain interval start", interval.contains(checkZdt));
        assertTrue("should contain interval start", interval.contains(checkTs));
    }
    @Test
    public void test0204_interval_contains_interval_end() {
        Interval interval = new CustomInterval(TEST_TIMEZONE, TEST_START, TEST_END);
        ZonedDateTime checkZdt = TEST_END.atZone(TEST_TIMEZONE.toZoneId());
        Instant checkTs = checkZdt.toInstant();
        assertTrue("should contain interval end", interval.contains(checkZdt));
        assertTrue("should contain interval end", interval.contains(checkTs));
    }

    // endregion
    // region - 03: different timezones checks

    @Test
    public void test0301_interval_contains_valid_timestamp_from_different_timezones() {
        final LocalDateTime start = TEST_START;
        final LocalDateTime end = TEST_START.plusDays(1).minusNanos(1);
        Interval intervalBase = new CustomInterval(TEST_TIMEZONE, start, end, 0);
        Interval intervalTest = new CustomInterval(TimeZone.getTimeZone(ZoneOffset.UTC), start, end, 0);
        assertTrue(
            "should contain test interval start",
                intervalBase.contains(
                    intervalTest.start()
                )
        );
        assertTrue(
            "should contain test interval start",
                intervalBase.contains(
                    intervalTest.getStart().atZone(intervalTest.timezone().toZoneId())
                )
        );
    }
    @Test
    public void test0302_interval_does_not_contain_invalid_timestamp_from_different_timezones() {
        final LocalDateTime start = TEST_START;
        final LocalDateTime end = TEST_START.plusDays(1).minusNanos(1);
        Interval intervalBase = new CustomInterval(TEST_TIMEZONE, start, end, 0);
        Interval intervalTest = new CustomInterval(TimeZone.getTimeZone(ZoneOffset.UTC), start, end, 0);
        assertFalse("should not contain test interval end", intervalBase.contains(intervalTest.end()));
        assertFalse(
            "should not contain test interval end",
            intervalBase.contains(
                intervalTest.getEnd().atZone(intervalTest.timezone().toZoneId())
            )
        );
    }
    @Test
    public void test0303_interval_contains_valid_timestamp_from_different_timezones_with_offset() {
        final LocalDateTime start = TEST_START;
        final LocalDateTime end = TEST_START.plusDays(1).minusNanos(1);
        Interval intervalBase = new CustomInterval(TEST_TIMEZONE, start, end, 5);
        Interval intervalTest = new CustomInterval(TimeZone.getTimeZone(ZoneOffset.UTC), start, end, -5);
        assertTrue("should contain test interval start", intervalBase.contains(intervalTest.start()));
        assertTrue(
            "should contain test interval start",
            intervalBase.contains(
                intervalTest.getStart().atZone(intervalTest.timezone().toZoneId())
            )
        );
    }
    @Test
    public void test0304_interval_does_not_contain_invalid_timestamp_from_different_timezones_with_offset() {
        final LocalDateTime start = TEST_START;
        final LocalDateTime end = TEST_START.plusDays(1).minusNanos(1);
        Interval intervalBase = new CustomInterval(TEST_TIMEZONE, start, end, 5);
        Interval intervalTest = new CustomInterval(TimeZone.getTimeZone(ZoneOffset.UTC), start, end);
        assertFalse("should not contain test interval end", intervalBase.contains(intervalTest.end()));
        assertFalse(
            "should not contain test interval end",
            intervalBase.contains(
                intervalTest.getEnd().atZone(intervalTest.timezone().toZoneId())
            )
        );
    }

    // endregion

}
