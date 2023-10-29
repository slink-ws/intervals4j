package ws.slink.intervals.exception;

public class InvalidMonthException extends InvalidIntervalException {

    private static final String MESSAGE_PREFIX = "invalid month";

    public InvalidMonthException() {
        super(MESSAGE_PREFIX);
    }
    public InvalidMonthException(String message) {
        super(message);
    }
    public InvalidMonthException(Throwable throwable) {
        super(throwable);
    }

}
