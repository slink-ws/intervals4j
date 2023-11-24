package ws.slink.intervals.exception;

/**
 * Exception thrown on attempt to create invalid day interval
 */
public class InvalidShiftValueException extends IllegalArgumentException {

    private static final String MESSAGE_PREFIX = "invalid shift value";

    public InvalidShiftValueException() {
        super(MESSAGE_PREFIX);
    }
    public InvalidShiftValueException(String input) {
        super(MESSAGE_PREFIX + ": " + input);
    }
    public InvalidShiftValueException(Throwable throwable) {
        super(MESSAGE_PREFIX, throwable);
    }

}
