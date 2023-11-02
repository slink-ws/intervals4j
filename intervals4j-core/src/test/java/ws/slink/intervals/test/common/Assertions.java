package ws.slink.intervals.test.common;

import org.junit.Assert;
import ws.slink.intervals.Interval;
import ws.slink.intervals.IntervalBuilder;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("CommentedOutCode")
public class Assertions {

    public static void assertBuilderTimezone(IntervalBuilder builder, TimeZone expected) {
        try {
            Field privateField = IntervalBuilder.class.getDeclaredField("timezone");
            privateField.setAccessible(true);
            TimeZone tz = (TimeZone)privateField.get(builder);
            assertEquals("incorrect timezone", expected, tz);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    public static void assertBuilderOffset(IntervalBuilder builder, int expected) {
        try {
            Field privateField = IntervalBuilder.class.getDeclaredField("offset");
            privateField.setAccessible(true);
            int offset = (int)privateField.get(builder);
            assertEquals("incorrect offset", expected, offset);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    public static void assertBuilderDateTime(IntervalBuilder builder, String timeField, LocalDateTime expected) {
        try {
            Field privateField = IntervalBuilder.class.getDeclaredField(timeField);
            privateField.setAccessible(true);
            LocalDateTime time = (LocalDateTime)privateField.get(builder);
            assertEquals("incorrect " + timeField, expected, time);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    public static void assertBuilderTime(IntervalBuilder builder, String timeField, LocalTime expected) {
        try {
            Field privateField = IntervalBuilder.class.getDeclaredField(timeField);
            privateField.setAccessible(true);
            LocalDateTime dt = (LocalDateTime)privateField.get(builder);
            LocalTime time = LocalTime.of(dt.getHour(), dt.getMinute(), dt.getSecond(), dt.getNano());
            assertEquals("incorrect " + timeField, expected, time);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    public static void assertBuilderDate(IntervalBuilder builder, String timeField, LocalDate expected) {
        try {
            Field privateField = IntervalBuilder.class.getDeclaredField(timeField);
            privateField.setAccessible(true);
            LocalDateTime dt = (LocalDateTime)privateField.get(builder);
            LocalDate date = LocalDate.of(dt.getYear(), dt.getMonth(), dt.getDayOfMonth());
            assertEquals("incorrect " + timeField, expected, date);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void assertUtcTimezone(Interval interval) {
        Assert.assertEquals("Should be UTC timezone", TestConfig.UTC_TIMEZONE, interval.timezone());
    }
    public static void assertGivenTimezone(Interval interval, TimeZone timeZone) {
        assertEquals("Should be " + timeZone.getID() + " timezone", timeZone, interval.timezone());
    }
    public static void assertGivenDateTime(LocalDateTime expected, LocalDateTime real) {
        assertEquals(expected, real);
    }

//    public static void assertReflectiveOffsetValue(Interval interval, int expected) {
//        try {
//            Field privateField = CustomInterval.class.getDeclaredField("offset");
//            privateField.setAccessible(true);
//            int offset = (int)privateField.get(interval);
//            assertEquals(expected, offset);
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
//    }

}
