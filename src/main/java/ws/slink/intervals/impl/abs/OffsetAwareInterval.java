package ws.slink.intervals.impl.abs;

import ws.slink.intervals.impl.CustomInterval;

import java.time.LocalDateTime;
import java.util.TimeZone;

public abstract class OffsetAwareInterval extends CustomInterval {

    protected int offset;

    protected OffsetAwareInterval(TimeZone timezone, LocalDateTime start, LocalDateTime end) {
        super(timezone, start, end, 0);
    }

    protected OffsetAwareInterval(TimeZone timezone, LocalDateTime start, LocalDateTime end, int offset) {
        super(timezone, start, end, offset);
        this.offset = offset;
    }

}