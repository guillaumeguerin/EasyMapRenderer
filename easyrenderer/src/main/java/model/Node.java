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

    public Node(Double identifier, Double latitude, Double longitude) {
        setId(identifier);
        setLat(latitude);
        setLon(longitude);
    }

    public Node(int identifier, int latitude, int longitude) {
        setId(Double.valueOf(identifier));
        setLat(Double.valueOf(latitude));
        setLon(Double.valueOf(longitude));
    }

    //Constructor
    public Node() {
        // Empty on purpose
    }

    public boolean equals(Node n) {
        return (this.getLat().equals(n.getLat()) && this.getLon().equals(n.getLon()));
    }
}
