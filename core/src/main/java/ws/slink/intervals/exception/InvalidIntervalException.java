package ws.slink.intervals.exception;

/**
 * Exception thrown on attempt to create invalid interval
 */
public class InvalidIntervalException extends RuntimeException {

    private static final String MESSAGE_PREFIX = "invalid interval";

    public InvalidIntervalException() {
        super(MESSAGE_PREFIX);
    }
    public InvalidIntervalException(String message) {
        super(message);
    }

    public InvalidIntervalException(Throwable throwable) {
        super(throwable);
    }

}
