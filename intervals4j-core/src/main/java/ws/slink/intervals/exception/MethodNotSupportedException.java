package ws.slink.intervals.exception;

/**
 * Exception thrown on attempt to call unsupported method
 */
public class MethodNotSupportedException extends RuntimeException {

    private static final String MESSAGE_PREFIX = "method not supported for current interval implementation";

    public MethodNotSupportedException() {
        super(MESSAGE_PREFIX);
    }

}
