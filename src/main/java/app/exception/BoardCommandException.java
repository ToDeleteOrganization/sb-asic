package app.exception;

public class BoardCommandException extends Exception {

    public BoardCommandException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
