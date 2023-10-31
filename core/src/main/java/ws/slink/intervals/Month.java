package ws.slink.intervals;

import ws.slink.intervals.impl.OffsetAwareInterval;
import ws.slink.intervals.tools.CalendarTools;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.TimeZone;

public class Month extends OffsetAwareInterval {

    public Month(TimeZone timezone, LocalDateTime start, LocalDateTime end) {
        this(timezone, start, end, 0);
    }
    public Month(TimeZone timezone, LocalDateTime start, LocalDateTime end, int offset) {
        super(timezone, start, end, offset);
        this.start = start.with(TemporalAdjusters.firstDayOfMonth()).with(CalendarTools.TO_START_OF_DAY).plusHours(offset);
        this.end = start.with(TemporalAdjusters.lastDayOfMonth()).with(CalendarTools.TO_END_OF_DAY).plusHours(offset);
    }

    @Override
    public Interval previous() {
        LocalDateTime prev = this.start.minusMonths(1);
        return IntervalBuilder.month(
            prev.getYear(),
            prev.getMonthValue(),
            this.timezone().getID(),
            offset
        );
    }

    @Override
    public Interval withPrevious() {
        return new IntervalBuilder()
            .timezone(this.timezone)
            .start(this.start.minusMonths(1))
            .end(this.end)
            .build();
    }

    public static Month of(String input) {
        return of(input, "UTC");
    }
    public static Month of(String input, String timezone) {
        return of(input, timezone, 0);
    }
    public static Month of(String input, int offset) {
        return of(input, "UTC", offset);
    }
    public static Month of(String input, String timezone, int offset) {
        Interval interval = IntervalBuilder.parse(input, timezone, offset);
        return IntervalBuilder.month(
            interval.getStart().getYear(),
            interval.getStart().getMonthValue(),
            timezone,
            offset
        );
    }

}
