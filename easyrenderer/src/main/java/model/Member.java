package model;

/**
 * Member model class
 */
public class Member implements Element {

    private Double id;
    private Double wayIsUsedByRelationId;
    private String role;

    public Double getId() {
        return id;
    }

    public Double getWayIsUsedByRelationId() {
        return wayIsUsedByRelationId;
    }

    public String getRole() {
        return role;
    }

    public void setId(Double d) {
        id = d;
    }

    public void setWayIsUsedByRelationId(Double id) {
        wayIsUsedByRelationId = id;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Member(Double myId, Double wayIsUsedByRelationId, String role) {
        setId(myId);
        setWayIsUsedByRelationId(wayIsUsedByRelationId);
        setRole(role);
    }

}
