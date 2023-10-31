package ws.slink.intervals.exception;

/**
 * Exception thrown on attempt to create invalid day interval
 */
public class InvalidDayException extends InvalidIntervalException {

    private static final String MESSAGE_PREFIX = "invalid day";

    public InvalidDayException() {
        super(MESSAGE_PREFIX);
    }
    public InvalidDayException(String input) {
        super(MESSAGE_PREFIX + ": " + input);
    }
    public InvalidDayException(Throwable throwable) {
        super(throwable);
    }

}
