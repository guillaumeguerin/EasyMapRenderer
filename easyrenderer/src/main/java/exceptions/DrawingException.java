package exceptions;

/**
 * Class for the OSM file drawing.
 */
public class DrawingException extends Exception {

    /**
     * Constructor with parent exception.
     *
     * @param e the parent exception
     */
    public DrawingException(Exception e) {
        super(e);
    }

    /**
     * Constructor with message
     *
     * @param message the message
     */
    public DrawingException(String message) {
        super(message);
    }
}
