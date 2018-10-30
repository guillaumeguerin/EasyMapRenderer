package parser;

import java.awt.Color;
import java.util.List;

import exceptions.ParseException;
import model.Bound;

public class XMLParserFacade {
	
	List<Object> oList;
	
	static Boolean way = false;
	static Boolean relation = false;
	static String wayText = "";
	static String relationText = "";
	
	public static Object build(String text) throws ParseException {
		Object o = new Object();
		if(text.trim().startsWith("<bounds") && !way) {
			o = new Bound(text);
		}
		else if(text.trim().startsWith("<node") && !way) {
			o = NodeParser.parse(text);
		}
		else if(text.trim().startsWith("<way") && !way && !relation) {
			way = true;
			wayText += text;
		}
		else if(text.trim().contains("</way") && way) {
			wayText += text;
			o = WayParser.parse(wayText);
			way = false;
			wayText = "";
		}
		else if(text.trim().startsWith("<relation") && !relation && !way) {
			relation = true;
			relationText += text;
		}
		else if(text.trim().contains("</relation") && relation) {
			relationText += text;
			o = RelationParser.parse(relationText);
			relation = false;
			relationText = "";
		}
		else if(way) {
			wayText += text;
		}
		else if(relation) {
			relationText += text;
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

