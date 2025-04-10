package co.com.nequi.model.user.exceptions;

public class UserException extends RuntimeException {
    private int statusCode;

    public UserException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
