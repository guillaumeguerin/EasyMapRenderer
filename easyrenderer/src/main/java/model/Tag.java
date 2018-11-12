package model;

import org.apache.log4j.Logger;

public class Tag implements Element {

    private static final Logger logger = Logger.getLogger(Tag.class);

    private Double id;
    private Double usedBy;
    private String type1;
    private String type2;

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

    public Tag(Double myId, Double usedBy, String type1, String type2) {
        setId(myId);
        setUsedBy(usedBy);
        setType1(type1);
        setType2(type2);
    }

}
