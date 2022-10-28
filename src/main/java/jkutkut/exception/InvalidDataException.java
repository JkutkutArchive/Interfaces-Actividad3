package jkutkut.exception;

/**
 * Exception to be thrown when the data is invalid.
 *
 * @author jkutkut
 */
public class InvalidDataException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidDataException(String s) {
        super(s);
    }

    public InvalidDataException(String s, int min, int max) {
        super(String.format("%s [%d, %d]", s, min, max));
    }
}