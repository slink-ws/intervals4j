package ws.slink.intervals.test.common;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.TimeZone;

public class TestConfig {

    public static final String TEST_TIMEZONE_STR = "Asia/Vladivostok";
    public static final TimeZone TEST_TIMEZONE = TimeZone.getTimeZone(TEST_TIMEZONE_STR);
    public static final TimeZone UTC_TIMEZONE = TimeZone.getTimeZone(ZoneOffset.UTC);

    public static final LocalDateTime TEST_START = LocalDateTime.of(
        2023,
        10,
        1,
        0,
        0,
        0,
        0
    );
    public static final LocalDateTime TEST_END = LocalDateTime.of(
        2023,
        10,
        31,
        23,
        59,
        59,
        999999999
    );

}
