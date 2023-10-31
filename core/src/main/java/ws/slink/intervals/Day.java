package ws.slink.intervals;

import ws.slink.intervals.impl.OffsetAwareInterval;
import ws.slink.intervals.tools.CalendarTools;

import java.time.LocalDateTime;
import java.util.TimeZone;

/**
 * `Day` implementation of Interval interface
 */
public class Day extends OffsetAwareInterval {

    public Day(TimeZone timezone, LocalDateTime start, LocalDateTime end) {
        this(timezone, start, end, 0);
    }
    public Day(TimeZone timezone, LocalDateTime start, LocalDateTime end, int offset) {
        super(timezone, start, end, offset);
        this.start = start.with(CalendarTools.TO_START_OF_DAY).plusHours(offset);
        this.end = start.with(CalendarTools.TO_END_OF_DAY).plusHours(offset);
        this.offset = offset;
    }

    @Override
    public Interval previous() {
        LocalDateTime prev = this.start.minusDays(1);
        return IntervalBuilder.day(
            prev.getYear(),
            prev.getMonthValue(),
            prev.getDayOfMonth(),
            this.timezone().getID(),
            offset
        );
    }

    @Override
    public Interval withPrevious() {
        return new IntervalBuilder()
            .timezone(this.timezone)
            .start(this.start.minusDays(1))
            .end(this.end)
            .build();
    }

    public static Day of(String input) {
        return of(input, "UTC");
    }
    public static Day of(String input, String timezone) {
        return of(input, timezone, 0);
    }
    public static Day of(String input, int offset) {
        return of(input, "UTC", offset);
    }
    public static Day of(String input, String timezone, int offset) {
        Interval interval = IntervalBuilder.parse(input, timezone, offset);
        return IntervalBuilder.day(
            interval.getStart().getYear(),
            interval.getStart().getMonthValue(),
            interval.getStart().getDayOfMonth(),
            timezone,
            offset
        );
    }

}
