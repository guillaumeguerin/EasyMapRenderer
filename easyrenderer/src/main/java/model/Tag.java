package model;

import java.util.ArrayList;
import java.util.List;

public class Tag implements Element {

	Double id;
	Double usedBy;
	String type1;
	String type2;
	
	public Double getId() {
		return id;
	}
	
	public Double getUsedBy() {
		return usedBy;
	}
	
	public String getType1() {
		return type1;
	}
	
	public String getType2() {
		return type2;
	}
		
	public void setId(Double d) {
		id = d;
	}
	
	public void setUsedBy(Double d) {
		usedBy = d;
	}
	
	public void setType1(String type) {
		type1 = type;
	}
	
	public void setType2(String type) {
		type2 = type;
	}

	public Tag(String l) {
		String[] tags = l.split("<");
		for(int i=0; i<tags.length; i++) {
			String[] line = tags[i].split(" "); 
			if(tags[i].contains("tag")) {
				if(line.length > 2 && line[1].split("=").length > 0 && line[2].split("=")[1].length() > 0) {
					String type1 = line[1].split("=")[1].replace("'", "");
					String type2 = line[2].split("=")[1].replace("'", "");
					if(type1.equalsIgnoreCase("natural")
							|| type1.equalsIgnoreCase("water")
							|| type1.equalsIgnoreCase("waterway")
							|| type1.equalsIgnoreCase("bridge")
							|| type1.equalsIgnoreCase("parking")
							|| type1.equalsIgnoreCase("wall")
							|| type1.equalsIgnoreCase("oneway")
							|| type1.equalsIgnoreCase("highway")
							|| type1.equalsIgnoreCase("railway")
							|| type1.equalsIgnoreCase("building")
							|| type1.equalsIgnoreCase("historic")
							|| type1.equalsIgnoreCase("area")
							|| type1.equalsIgnoreCase("surface")
							|| type1.equalsIgnoreCase("lane")
							|| type1.equalsIgnoreCase("military")
							|| type1.equalsIgnoreCase("barrier")
							|| type1.equalsIgnoreCase("lanes")
							|| type1.equalsIgnoreCase("tunnel")) {
						setType1(type1);
						setType2(type2);
					}
					else {
						if(!type1.equalsIgnoreCase("source")) {
							System.out.println("Unused type : " + type1);
						}
					}
				}
			}
		}
	}
	
	public Tag(Double myId, Double usedBy, String type1, String type2) {
		setId(myId);
		setUsedBy(usedBy);
		setType1(type1);
		setType2(type2);
	}

	public Tag() {
		// TODO Auto-generated constructor stub
	}

	public Tag(double id) {
		setId(id);
	}
}
