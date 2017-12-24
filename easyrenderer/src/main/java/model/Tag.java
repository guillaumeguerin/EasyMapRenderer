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
