package parser;

import exceptions.ParseException;
import model.Element;

/**
 * Parent class for the project parsers.
 */
public class Parser {

    public static Element parse(String s) throws ParseException {
        throw new ParseException("Not a valid Parser");
    }
}
