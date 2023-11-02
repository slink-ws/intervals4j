package ws.slink.intervals.exception;

/**
 * Exception thrown on attempt to create invalid year interval
 */
public class InvalidYearException extends InvalidIntervalException {

    private static final String MESSAGE_PREFIX = "invalid year";

    public InvalidYearException() {
        super(MESSAGE_PREFIX);
    }
    public InvalidYearException(String message) {
        super(MESSAGE_PREFIX + ": " + message);
    }
    public InvalidYearException(Throwable throwable) {
        super(throwable);
    }

}
