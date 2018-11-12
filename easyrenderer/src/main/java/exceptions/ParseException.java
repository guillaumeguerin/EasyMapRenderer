package exceptions;

/**
 * Generic exception for parsing
 */
public class ParseException extends Exception {

    /**
     * Constructor with parent exception.
     *
     * @param e the parent exception
     */
    public ParseException(Exception e) {
        super(e);
    }

    /**
     * Constructor with message
     *
     * @param message the message
     */
    public ParseException(String message) {
        super(message);
    }
}
