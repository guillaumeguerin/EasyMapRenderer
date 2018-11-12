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

    public Tag(String l) {
        String[] tags = l.split("<");
        for (int i = 0; i < tags.length; i++) {
            String[] line = tags[i].split(" ");
            if (tags[i].contains("tag")) {
                if (line.length > 2 && line[1].split("=").length > 0 && line[2].split("=")[1].length() > 0) {
                    String lineType1 = line[1].split("=")[1].replace("'", "");
                    String lineType2 = line[2].split("=")[1].replace("'", "");
                    if (lineType1.equalsIgnoreCase("natural")
                            || lineType1.equalsIgnoreCase("water")
                            || lineType1.equalsIgnoreCase("waterway")
                            || lineType1.equalsIgnoreCase("bridge")
                            || lineType1.equalsIgnoreCase("parking")
                            || lineType1.equalsIgnoreCase("wall")
                            || lineType1.equalsIgnoreCase("oneway")
                            || lineType1.equalsIgnoreCase("highway")
                            || lineType1.equalsIgnoreCase("railway")
                            || lineType1.equalsIgnoreCase("building")
                            || lineType1.equalsIgnoreCase("historic")
                            || lineType1.equalsIgnoreCase("area")
                            || lineType1.equalsIgnoreCase("surface")
                            || lineType1.equalsIgnoreCase("lane")
                            || lineType1.equalsIgnoreCase("military")
                            || lineType1.equalsIgnoreCase("barrier")
                            || lineType1.equalsIgnoreCase("lanes")
                            || lineType1.equalsIgnoreCase("tunnel")) {
                        setType1(lineType1);
                        setType2(lineType2);
                    } else {
                        if (!lineType1.equalsIgnoreCase("source")) {
                            logger.info("Unused type : " + lineType1);
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

}
