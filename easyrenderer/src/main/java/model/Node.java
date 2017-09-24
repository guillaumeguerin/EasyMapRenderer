package model;

public class Node implements Element {

	Double lat;
	Double lon;
	Double id;
	
	public Double getLat() {
		return lat;
	}
	
	public Double getLon() {
		return lon;
	}
	
	public Double getId() {
		return id;
	}
	
	public void setLat(Double d) {
		lat = d;
	}
	
	public void setLon(Double d) {
		lon = d;
	}
	
	public void setId(Double d) {
		id = d;
	}
	
	public Node (Double identifier, Double latitude, Double longitude) {
		setId(identifier);
		setLat(latitude);
		setLon(longitude);
	}

	public Node (int identifier, int latitude, int longitude) {
		setId(new Double(identifier));
		setLat(new Double(latitude));
		setLon(new Double(longitude));
	}
	
	public Node() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean equals (Node n) {
		if(this.getLat().equals(n.getLat()) && this.getLon().equals(n.getLon())) {
			return true;
		}
		return false;
	}
}
