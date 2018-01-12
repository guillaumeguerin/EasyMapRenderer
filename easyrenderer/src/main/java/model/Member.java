package model;

import java.util.ArrayList;
import java.util.List;

public class Member implements Element {

	Double id;
	Double usedBy;
	Double relationId;
	String role;
	
	public Double getId() {
		return id;
	}
	
	public Double getUsedBy() {
		return usedBy;
	}
	
	public Double getRelationId() {
		return relationId;
	}
	
	public String getRole() {
		return role;
	}
		
	public void setId(Double d) {
		id = d;
	}
	
	public void setRelationId(Double d) {
		relationId = d;
	}
	
	public void setUsedBy(Double d) {
		usedBy = d;
	}
	
	public void setRole(String role) {
		this.role = role;
	}

	public Member(String l) {
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
						setRole(type1);
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
	
	public Member(Double myId, Double usedBy, String role) {
		setId(myId);
		setUsedBy(usedBy);
		setRole(role);
	}
	
	public Member(Double myId, Double usedBy, Double relationId, String role) {
		setId(myId);
		setUsedBy(usedBy);
		setRole(role);
	}

	public Member() {
		// TODO Auto-generated constructor stub
	}

	public Member(double id) {
		setId(id);
	}
}
