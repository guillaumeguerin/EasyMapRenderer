package model;

import org.apache.log4j.Logger;

public class Member implements Element {

    private static final Logger logger = Logger.getLogger(Member.class);

    private Double id;
    private Double usedBy;
    private Double relationId;
    private String role;

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
        for (int i = 0; i < tags.length; i++) {
            String[] line = tags[i].split(" ");
            if (tags[i].contains("tag")) {
                if (line.length > 2 && line[1].split("=").length > 0 && line[2].split("=")[1].length() > 0) {
                    String type1 = line[1].split("=")[1].replace("'", "");
                    //String type2 = line[2].split("=")[1].replace("'", "");
                    if (type1.equalsIgnoreCase("natural")
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
                        setRole(type1);
                    } else {
                        if (!type1.equalsIgnoreCase("source")) {
                            logger.info("Unused type : " + type1);
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

    // Constructor
    public Member() {
        // Empty on purpose
    }

    public Member(double id) {
        setId(id);
    }

}
