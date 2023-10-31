package ws.slink.intervals.impl;

import java.time.LocalDateTime;
import java.util.TimeZone;

public abstract class OffsetAwareInterval extends CustomInterval {

    protected int offset;

    protected OffsetAwareInterval(TimeZone timezone, LocalDateTime start, LocalDateTime end, int offset) {
        super(timezone, start, end, offset);
        this.offset = offset;
    }

}