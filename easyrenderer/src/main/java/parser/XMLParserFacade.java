package parser;

import java.awt.Color;
import java.util.List;

import exceptions.ParseException;
import model.Bound;
import model.Node;
import model.Way;

public class XMLParserFacade {
	
	List<Object> oList;
	
	static Boolean way = false;
	static String wayText = "";
	
	public static Object build(String text) throws ParseException {
		Object o = new Object();
		if(text.trim().startsWith("<bounds") && !way) {
			o = new Bound(text);
		}
		else if(text.trim().startsWith("<node") && !way) {
			o = NodeParser.parse(text);
		}
		else if(text.trim().startsWith("<relation") && !way) {
			o = RelationParser.parse(text);
		}
		else if(text.trim().startsWith("<way")) {
			way = true;
			wayText += text;
		}
		else if(text.trim().contains("</way")) {
			wayText += text;
			o = WayParser.parse(wayText);
			way = false;
			wayText = "";
		}
		else if(way) {
			wayText += text;
		}
		return o;
	}

	
	public Color parseColor(String attribute) throws ParseException {
		if(attribute.startsWith("#")) {
			return hex2Rgb(attribute.replace("#", ""));
		}
		else if(attribute.startsWith("rgb(")) {
			String[] rgbValues = attribute.replace("rgb(","").replace(")", "").split(",");
			return new Color(Integer.parseInt(rgbValues[0]), Integer.parseInt(rgbValues[1]), Integer.parseInt(rgbValues[2]));
		}
		throw new ParseException("Cannot parse element : " + attribute + " to color.");
	}
	
	public static Color hex2Rgb(String colorStr) {
	    return new Color(
	            Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
	            Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
	            Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
	}
}

