package parser;

import exceptions.ParseException;
import model.Bound;

/**
 * Class for XML Parsing
 */
public class XMLParserFacade {

    static Boolean way = false;
    static Boolean relation = false;
    static String wayText = "";
    static String relationText = "";

    /**
     * Private constructor to hide the implicit one.
     */
    private XMLParserFacade() {
        //Empty on purpose.
    }

    public static Object build(String text) throws ParseException {
        Object o = new Object();
        if (text.trim().startsWith("<bounds") && !way) {
            o = new Bound(text);
        } else if (text.trim().startsWith("<node") && !way) {
            o = NodeParser.parse(text);
        } else if (text.trim().startsWith("<way") && !way && !relation) {
            way = true;
            wayText += text;
        } else if (text.trim().contains("</way") && way) {
            wayText += text;
            o = WayParser.parse(wayText);
            way = false;
            wayText = "";
        } else if (text.trim().startsWith("<relation") && !relation && !way) {
            relation = true;
            relationText += text;
        } else if (text.trim().contains("</relation") && relation) {
            relationText += text;
            o = RelationParser.parse(relationText);
            relation = false;
            relationText = "";
        } else if (way) {
            wayText += text;
        } else if (relation) {
            relationText += text;
        }
        return o;
    }
}

