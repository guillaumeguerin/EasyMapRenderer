package model;

import java.util.ArrayList;
import java.util.List;

public class Relation implements Element {

	Double id;
	List<Double> ways;
	List<Tag> tags;
	
	public Double getId() {
		return id;
	}
	
	public List<Double> getWays() {
		return ways;
	}
	
	public List<Tag> getTags() {
		return tags;
	}
	
	public void setId(Double d) {
		id = d;
	}
	
	public void setWays(List<Double> w) {
		ways = w;
	}
	
	public void addWay(Double w) {
		ways.add(w);
	}
	
	public void setTags(List<Tag> t) {
		tags = t;
	}
	
	public void addTag(Tag t) {
		tags.add(t);
	}
	
	/*public Relation(String l) {
		setNodes(new ArrayList<Double>());
		String[] tags = l.split("<");
		for(int i=0; i<tags.length; i++) {
			String[] line = tags[i].split(" "); 
			if(tags[i].contains("nd ref=")) {
				if(line.length > 0 && line[1].split("=").length > 0) {
					String nodeId = line[1].split("=")[1].replace("'", "");
					try {
						addNode(Double.parseDouble(nodeId));
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
						setId(Double.parseDouble(wayId));
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
						setType1(type1);
						setType2(type2);
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
	}*/
	
	public Relation(Double myId, List<Double> waysId, List<Tag> tags) {
		setId(myId);
		setWays(ways);
		setTags(tags);
	}

	public Relation() {
		// TODO Auto-generated constructor stub
	}
}
