package Exceptions;

public class InvalidTaskDetailsException extends RuntimeException {
    public InvalidTaskDetailsException(String message) {
        super(message);
    }
}
