package model;

public class Bound implements Element {

	Double minLat;
	Double minLon;
	Double maxLat;
	Double maxLon;
	
	public Double getMinLat() {
		return minLat;
	}
	
	public Double getMinLon() {
		return minLon;
	}
	
	public Double getMaxLat() {
		return maxLat;
	}
	public Double getMaxLon() {
		return maxLon;
	}
	
	public void setMinLat(Double d) {
		minLat = d;
	}
	
	public void setMinLon(Double d) {
		minLon = d;
	}
	
	public void setMaxLat(Double d) {
		maxLat = d;
	}
	public void setMaxLon(Double d) {
		maxLon = d;
	}
	
	public Bound (String l) {
		String[] line = l.split(" ");
		for(int i=0; i<line.length; i++) {
			String s = "";
			if(line[i].startsWith("minlat")) {
				s = line[i].split("=")[1].replace("'", "");
				setMinLat(Double.parseDouble(s));
			}
			else if(line[i].startsWith("minlat")) {
				s = line[i].split("=")[1].replace("'", "");
				setMinLat(Double.parseDouble(s));
			}
			else if(line[i].startsWith("minlon")) {
				s = line[i].split("=")[1].replace("'", "");
				setMinLat(Double.parseDouble(s));
			}
			else if(line[i].startsWith("maxlat")) {
				s = line[i].split("=")[1].replace("'", "");
				setMinLat(Double.parseDouble(s));
			}
			else if(line[i].startsWith("maxlon")) {
				s = line[i].split("=")[1].replace("'", "");
				setMinLat(Double.parseDouble(s));
			}
			else {
				if(line[i].length() > 1 && (!line[i].startsWith("<") || !line[i].endsWith(">"))) {
					System.out.println("Unknown property: " + line[i]);
				}
				
			}
		}
	}
}
