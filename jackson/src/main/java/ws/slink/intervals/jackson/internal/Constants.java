package ws.slink.intervals.jackson.internal;

import java.time.format.DateTimeFormatter;

public final class Constants {

    private Constants() {
        // private default constructor to prevent class instantiation
    }

    public static final String TYPE_FIELD = "type";
    public static final String TIMEZONE_FIELD = "timezone";
    public static final String START_FIELD = "start";
    public static final String END_FIELD = "end";

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

}
