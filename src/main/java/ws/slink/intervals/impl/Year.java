package ws.slink.intervals.impl;

import ws.slink.intervals.Interval;
import ws.slink.intervals.IntervalBuilder;
import ws.slink.intervals.impl.abs.OffsetAwareInterval;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.TimeZone;

import static ws.slink.intervals.tools.CalendarTools.TO_END_OF_DAY;
import static ws.slink.intervals.tools.CalendarTools.TO_START_OF_DAY;

public class Year extends OffsetAwareInterval {

    public Year(TimeZone timezone, LocalDateTime start, LocalDateTime end) {
        this(timezone, start, end, 0);
    }
    public Year(TimeZone timezone, LocalDateTime start, LocalDateTime end, int offset) {
        super(timezone, start, end, offset);
        this.start = start.with(TemporalAdjusters.firstDayOfYear()).with(TO_START_OF_DAY).plusHours(offset);
        this.end = start.with(TemporalAdjusters.lastDayOfYear()).with(TO_END_OF_DAY).plusHours(offset);
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

}
