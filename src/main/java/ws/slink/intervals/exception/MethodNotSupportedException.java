package ws.slink.intervals.exception;

public class MethodNotSupportedException extends RuntimeException {

    private static final String MESSAGE_PREFIX = "method not supported for current interval implementation";

    public MethodNotSupportedException() {
        super(MESSAGE_PREFIX);
    }

}
