package ws.slink.intervals;

import ws.slink.intervals.impl.OffsetAwareInterval;
import ws.slink.intervals.tools.CalendarTools;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.TimeZone;

public class Year extends OffsetAwareInterval {

    public Year(TimeZone timezone, LocalDateTime start, LocalDateTime end) {
        this(timezone, start, end, 0);
    }
    public Year(TimeZone timezone, LocalDateTime start, LocalDateTime end, int offset) {
        super(timezone, start, end, offset);
        this.start = start.with(TemporalAdjusters.firstDayOfYear()).with(CalendarTools.TO_START_OF_DAY).plusHours(offset);
        this.end = start.with(TemporalAdjusters.lastDayOfYear()).with(CalendarTools.TO_END_OF_DAY).plusHours(offset);
    }

    @Override
    public Interval previous() {
        return IntervalBuilder.year(this.start.getYear() - 1, this.timezone().getID(), offset);
    }
    @Override
    public Interval withPrevious() {
        return new IntervalBuilder()
            .timezone(this.timezone)
            .start(this.start.minusYears(1))
            .end(this.end)
            .build();
    }

    public static Year of(String input) {
        return of(input, "UTC");
    }
    public static Year of(String input, String timezone) {
        return of(input, timezone, 0);
    }
    public static Year of(String input, int offset) {
        return of(input, "UTC", offset);
    }
    public static Year of(String input, String timezone, int offset) {
        Interval interval = IntervalBuilder.parse(input, timezone, offset);
        return IntervalBuilder.year(interval.getStart().getYear(), timezone, offset);
    }

}
