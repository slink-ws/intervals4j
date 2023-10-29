package ws.slink.intervals.impl;

import ws.slink.intervals.Interval;
import ws.slink.intervals.IntervalBuilder;
import ws.slink.intervals.impl.abs.OffsetAwareInterval;

import java.time.LocalDateTime;
import java.util.TimeZone;

import static ws.slink.intervals.tools.CalendarTools.TO_END_OF_DAY;
import static ws.slink.intervals.tools.CalendarTools.TO_START_OF_DAY;

public class Day extends OffsetAwareInterval {

    public Day(TimeZone timezone, LocalDateTime start, LocalDateTime end) {
        this(timezone, start, end, 0);
    }
    public Day(TimeZone timezone, LocalDateTime start, LocalDateTime end, int offset) {
        super(timezone, start, end, offset);
        this.start = start.with(TO_START_OF_DAY).plusHours(offset);
        this.end = start.with(TO_END_OF_DAY).plusHours(offset);
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

}
