package ws.slink.intervals;

import ws.slink.intervals.exception.MethodNotSupportedException;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.TimeZone;

/**
 * generic interval interface
 *
 * @author Mikhail Kantur
 *
 */
public interface Interval {

    /**
     * returns interval timezone (default one is UTC)
     *
     * @return interval timezone
     */
    TimeZone timezone();

    /**
     * get interval start local date time
     *
     * @return interval start
     */
    LocalDateTime getStart();

    /**
     * get interval end local date time
     *
     * @return interval end
     */
    LocalDateTime getEnd();

    /**
     * get interval start timestamp
     *
     * @return interval start
     */
    Instant start();

    /**
     * get interval end timestamp
     *
     * @return interval end
     */
    Instant end();

    /**
     * checks if interval contains instant timestamp
     * - input value should be non-null
     * - no null-check is performed by this function
     *
     * @return previous Interval
     */
    boolean contains(Instant value);

    /**
     * checks if interval contains ZonedDateTime timestamp
     * - input value should be non-null
     * - no null-check is performed by this function
     *
     * @return previous Interval
     */
    default boolean contains(ZonedDateTime value) {
        return contains(value.toInstant());
    }

    /**
     * returns previous interval from current
     * (only supported for fixed type intervals, such as day, month, year)
     *
     * @return previous Interval
     */
    default Interval previous() {
        throw new MethodNotSupportedException();
    }

    /**
     * returns extended interval including previous one
     * (only supported for fixed type intervals, such as day, month, year)
     *
     * @return previous Interval
     */
    default Interval withPrevious() {
        throw new MethodNotSupportedException();
    }

    /**
     * returns interval shifted by duration, parsed from duration string.
     * duration string can be prepended with '-' sign to indicate that we should shift 'left',
     * without it, we'll shift 'right'
     *
     * @return shifted Interval
     */
    default Interval shifted(String duration) {
        if (duration == null || "".equals(duration.trim())) {
            return this;
        }
        Duration d;
        if (duration.startsWith("-")) {
            return shifted(Duration.parse(duration.substring(1)), false);
        } else {
            return shifted(Duration.parse(duration));
        }
    }

    /**
     * returns interval shifted forward by duration.
     *
     * @return shifted Interval
     */
    default Interval shifted(Duration duration) {
        return shifted(duration, true);
    }

    /**
     * returns interval shifted by duration.
     * if 'shiftForward' is true, we'll shift 'right', else we'll shift 'left'
     *
     * @return shifted Interval
     */
    default Interval shifted(Duration duration, boolean shiftForward) {
        throw new MethodNotSupportedException();
    }

}
