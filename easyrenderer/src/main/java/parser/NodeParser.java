package parser;

import exceptions.ParseException;
import model.Element;
import model.Node;

public class NodeParser extends Parser{

	public static Node parse(String l) throws ParseException{
		Node myNode = new Node();
		String[] line = l.split(" ");
		for(int i=0; i<line.length; i++) {
			String s = "";
			if(line[i].startsWith("lat")) {
				s = line[i].split("=")[1].replace("'", "");
				myNode.setLon(Double.parseDouble(s));
			}
			else if(line[i].startsWith("lon")) {
				s = line[i].split("=")[1].replace("'", "").replaceAll(">", "");
				myNode.setLat(Double.parseDouble(s));
			}
			else if(line[i].startsWith("id")) {
				s = line[i].split("=")[1].replace("'", "");
				myNode.setId(Double.parseDouble(s));
			}
			else {
				if(line[i].length() > 1 && (!line[i].startsWith("<") || !line[i].endsWith(">"))) {
					//System.out.println("Unknown property: " + line[i]);
				}
				
			}
		}
		if(myNode.getLat() == null || myNode.getLon() == null) {
			throw new ParseException("Could not parse Node from line : " + l);
		}
		return myNode;
	}

}
