package ws.slink.intervals.test.common;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.TimeZone;

public class TestConfig {

    public static final String TEST_TIMEZONE_STR = "Asia/Vladivostok";
    public static final TimeZone TEST_TIMEZONE = TimeZone.getTimeZone(TEST_TIMEZONE_STR);
    public static final TimeZone UTC_TIMEZONE = TimeZone.getTimeZone(ZoneOffset.UTC);

    public static final String TEST_START_DAY_STR = "2023-10-15";
    public static final String TEST_END_DAY_STR = "2023-11-18";

    public static final String TEST_START_MONTH_STR = "2023-10";
    public static final String TEST_END_MONTH_STR = "2023-11";

    public static final String TEST_START_YEAR_STR = "2023";
    public static final String TEST_END_YEAR_STR = "2024";

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
