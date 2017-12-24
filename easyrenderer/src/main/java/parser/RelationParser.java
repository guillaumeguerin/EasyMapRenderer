package parser;

import java.util.ArrayList;

import exceptions.ParseException;
import model.Relation;
import model.Tag;
import model.Way;

public class RelationParser {

	public static Relation parse(String l) throws ParseException {
		Relation myRelation = new Relation();
		myRelation.setWays(new ArrayList<Double>());
		String[] tags = l.split("<");
		for(int i=0; i<tags.length; i++) {
			String[] line = tags[i].split(" "); 
			if(tags[i].contains("nd ref=")) {
				if(line.length > 0 && line[1].split("=").length > 0) {
					String nodeId = line[1].split("=")[1].replace("'", "");
					try {
						myRelation.addWay(Double.parseDouble(nodeId));
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
						myRelation.setId(Double.parseDouble(wayId));
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
					myRelation.addTag(new Tag(1., 1., type1, type2));
				}
			}
			//System.out.println(tags[i]);
		}
		if(myRelation.getId() == null && (myRelation.getWays() == null || myRelation.getWays().size() == 0)) {
			throw new ParseException("Could not parse Way from line : " + l);
		}
		return myRelation;
	}
}
