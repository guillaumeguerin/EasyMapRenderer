package model;

import org.apache.log4j.Logger;

/**
 * Model for the bound xml element found in osm file
 */
public class Bound implements Element {

    private static final Logger logger = Logger.getLogger(Bound.class);

    private Double minLat;
    private Double minLon;
    private Double maxLat;
    private Double maxLon;

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

    public Bound(String l) {
        String[] line = l.split(" ");
        for (int i = 0; i < line.length; i++) {
            String s = "";
            if (line[i].startsWith("minlat")) {
                s = line[i].split("=")[1].replace("'", "");
                setMinLat(Double.parseDouble(s));
            } else if (line[i].startsWith("minlon")) {
                s = line[i].split("=")[1].replace("'", "");
                setMinLat(Double.parseDouble(s));
            } else if (line[i].startsWith("maxlat")) {
                s = line[i].split("=")[1].replace("'", "");
                setMinLat(Double.parseDouble(s));
            } else if (line[i].startsWith("maxlon")) {
                s = line[i].split("=")[1].replace("'", "");
                setMinLat(Double.parseDouble(s));
            } else {
                if (line[i].length() > 1 && (!line[i].startsWith("<") || !line[i].endsWith(">"))) {
                    logger.info("Unknown property: " + line[i]);
                }

            }
        }
    }
}
