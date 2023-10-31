package ws.slink.intervals.exception;

/**
 * Exception thrown on attempt to create invalid month interval
 */
public class InvalidMonthException extends InvalidIntervalException {

    private static final String MESSAGE_PREFIX = "invalid month";

    public InvalidMonthException() {
        super(MESSAGE_PREFIX);
    }
    public InvalidMonthException(String message) {
        super(MESSAGE_PREFIX + ": " + message);
    }
    public InvalidMonthException(Throwable throwable) {
        super(throwable);
    }

}
