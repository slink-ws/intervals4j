package ws.slink.intervals;

import ws.slink.intervals.exception.MethodNotSupportedException;

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
     * @return true if value is within interval
     */
    boolean contains(Instant value);

    /**
     * checks if interval contains ZonedDateTime timestamp
     * - input value should be non-null
     * - no null-check is performed by this function
     *
     * @return true if value is within interval
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
     * @return extended Interval with previous one
     */
    default Interval withPrevious() {
        throw new MethodNotSupportedException();
    }

}
