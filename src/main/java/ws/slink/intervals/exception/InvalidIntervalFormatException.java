package ws.slink.intervals.exception;

public class InvalidIntervalFormatException extends RuntimeException {

    private static final String MESSAGE_PREFIX = "invalid interval formatted string";

    public InvalidIntervalFormatException() {
        super(MESSAGE_PREFIX);
    }
    public InvalidIntervalFormatException(String input) {
        super(MESSAGE_PREFIX + ": " + input);
    }

}
