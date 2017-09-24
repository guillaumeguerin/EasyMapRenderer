package parser;

import java.util.ArrayList;

import exceptions.ParseException;
import model.Way;

public class WayParser extends Parser {

	public static Way parse(String l) throws ParseException {
		Way myWay = new Way();
		myWay.setNodes(new ArrayList<Double>());
		String[] tags = l.split("<");
		for(int i=0; i<tags.length; i++) {
			String[] line = tags[i].split(" "); 
			if(tags[i].contains("nd ref=")) {
				if(line.length > 0 && line[1].split("=").length > 0) {
					String nodeId = line[1].split("=")[1].replace("'", "");
					try {
						myWay.addNode(Double.parseDouble(nodeId));
					}
					catch(Exception e) {
						System.out.println(nodeId + e.getMessage());
					}

				}
			}
			else if(tags[i].contains("id=")) {
				if(line.length > 0 && line[1].split("=").length > 0) {
					String wayId = line[1].split("=")[1].replace("'", "");
					try {
						myWay.setId(Double.parseDouble(wayId));
					}
					catch(Exception e) {
						System.out.println(wayId + e.getMessage());
					}

				}
			}
			else if(tags[i].contains("tag")) {
				if(line.length > 2 && line[1].split("=").length > 0 && line[2].split("=")[1].length() > 0) {
					String type1 = line[1].split("=")[1].replace("'", "");
					String type2 = line[2].split("=")[1].replace("'", "");
					if(type1.toLowerCase().equals("natural")
							|| type1.toLowerCase().equals("water")
							|| type1.toLowerCase().equals("waterway")
							|| type1.toLowerCase().equals("bridge")
							|| type1.toLowerCase().equals("parking")
							|| type1.toLowerCase().equals("wall")
							|| type1.toLowerCase().equals("oneway")
							|| type1.toLowerCase().equals("highway")
							|| type1.toLowerCase().equals("railway")
							|| type1.toLowerCase().equals("building")
							|| type1.toLowerCase().equals("historic")
							|| type1.toLowerCase().equals("area")
							|| type1.toLowerCase().equals("surface")
							|| type1.toLowerCase().equals("lane")
							|| type1.toLowerCase().equals("military")
							|| type1.toLowerCase().equals("barrier")
							|| type1.toLowerCase().equals("lanes")
							|| type1.toLowerCase().equals("tunnel")) {
						myWay.setType1(type1);
						myWay.setType2(type2);
					}
					else {
						if(!type1.toLowerCase().equals("source")) {
							System.out.println("Unused type : " + type1);
						}
					}
				}
			}
			//System.out.println(tags[i]);
		}
		if(myWay.getId() == null && (myWay.getNodes() == null || myWay.getNodes().size() == 0)) {
			throw new ParseException("Could not parse Way from line : " + l);
		}
		return myWay;
	}
	
}