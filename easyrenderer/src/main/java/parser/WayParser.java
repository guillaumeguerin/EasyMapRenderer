package parser;

import java.util.ArrayList;

import exceptions.ParseException;
import model.Node;
import model.Tag;
import model.Way;

public class WayParser extends Parser {

	public static Way parse(String l) throws ParseException {
		Way myWay = new Way();
		myWay.setNodes(new ArrayList<Node>());
		String[] tags = l.split("<");
		for(int i=0; i<tags.length; i++) {
			String[] line = tags[i].split(" "); 
			if(tags[i].contains("nd ref=")) {
				if(line.length > 0 && line[1].split("=").length > 0) {
					String nodeId = line[1].split("=")[1].replace("'", "");
					try {
						myWay.addNode(new Node(Double.parseDouble(nodeId), 1., 1.));
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
					myWay.addTag(new Tag(1., 1., type1, type2));
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