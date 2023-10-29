package ws.slink.intervals.test;

import org.junit.Test;
import ws.slink.intervals.tools.CalendarTools;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;

import static org.junit.Assert.assertEquals;

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

}
