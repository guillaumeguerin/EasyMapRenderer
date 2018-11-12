package model;

/**
 * Node model class
 */
public class Node implements Element {

    private Double latitude;
    private Double longitude;
    private Double id;

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getId() {
        return id;
    }

    public void setLatitude(Double d) {
        latitude = d;
    }

    public void setLongitude(Double d) {
        longitude = d;
    }

    public void setId(Double d) {
        id = d;
    }

    public Node(Double identifier, Double latitude, Double longitude) {
        setId(identifier);
        setLatitude(latitude);
        setLongitude(longitude);
    }

    public Node(int identifier, int latitude, int longitude) {
        setId(Double.valueOf(identifier));
        setLatitude(Double.valueOf(latitude));
        setLongitude(Double.valueOf(longitude));
    }

    //Constructor
    public Node() {
        // Empty on purpose
    }

    public boolean equals(Node n) {
        return (this.getLatitude().equals(n.getLatitude()) && this.getLongitude().equals(n.getLongitude()));
    }
}
