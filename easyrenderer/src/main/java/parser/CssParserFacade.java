package parser;

import java.util.List;

import exceptions.ParseException;
import model.Bound;
import model.Node;
import model.Way;

public class CssParserFacade {
	
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

}

