package model;

/**
 * Member model class
 */
public class Member implements Element {

    private Double id;
    private Double usedBy;
    private String role;

    public Double getId() {
        return id;
    }

    public Double getUsedBy() {
        return usedBy;
    }

    public String getRole() {
        return role;
    }

    public void setId(Double d) {
        id = d;
    }

    public void setUsedBy(Double d) {
        usedBy = d;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Member(Double myId, Double usedBy, String role) {
        setId(myId);
        setUsedBy(usedBy);
        setRole(role);
    }

}
