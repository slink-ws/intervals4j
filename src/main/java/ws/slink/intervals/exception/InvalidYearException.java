package ws.slink.intervals.exception;

public class InvalidYearException extends InvalidIntervalException {

    private static final String MESSAGE_PREFIX = "invalid year";

    public InvalidYearException() {
        super(MESSAGE_PREFIX);
    }
    public InvalidYearException(String message) {
        super(message);
    }
    public InvalidYearException(Throwable throwable) {
        super(throwable);
    }

}
