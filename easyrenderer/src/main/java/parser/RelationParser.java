package parser;

import exceptions.ParseException;
import model.Member;
import model.Relation;
import model.Tag;
import org.apache.log4j.Logger;
import preferences.UserDesignSingleton;

import java.util.ArrayList;

public class RelationParser {

    private static final Logger logger = Logger.getLogger(RelationParser.class);

    /**
     * Constructor
     */
    private RelationParser() {
        //Empty on purpose.
    }

    public static Relation parse(String l) throws ParseException {
        Relation myRelation = new Relation();
        myRelation.setWays(new ArrayList<Double>());
        UserDesignSingleton designSingleton = UserDesignSingleton.getInstance();

        String[] tags = l.split("<");

        for (int i = 0; i < tags.length; i++) {
            String[] line = tags[i].split(" ");

            if (checkIfNode(tags, line, i)) {
                String nodeId = line[1].split("=")[1].replace("'", "");
                try {
                    myRelation.addWay(Double.parseDouble(nodeId));
                } catch (Exception e) {
                    logger.error(e);
                    logger.debug("Node id was : " + nodeId);
                }
            } else if (checkIfWay(tags, line, i)) {
                String wayId = line[1].split("=")[1].replace("'", "");
                //Then remove one if we have it in the list of ways
                //Then if the unused ways are outer of a multi polygon we draw the background of the map
                try {
                    myRelation.setId(Double.parseDouble(wayId));
                } catch (Exception e) {
                    logger.error(e);
                    logger.debug("Way id was : " + wayId);
                }
            } else if (checkIfTag(tags, line, i)) {
                String type1 = line[1].split("=")[1].replace("'", "");
                String type2 = line[2].split("=")[1].replace("'", "");
                myRelation.addTag(new Tag(1., 1., type1, type2));
            } else if (checkIfMember(tags, line, i)) {
                String wayId = line[2].split("=")[1].replace("'", "");
                String role = line[3].split("=")[1].replace("'", "");
                Double wayNumber = Double.parseDouble(wayId);
                myRelation.addWay(Double.parseDouble(wayId));
                myRelation.addMember(new Member(1., wayNumber, role));
                //TODO: add way id to list of all ways possible found
                if ("outer".equals(role)) {
                    designSingleton.getAllWaysPossible().add(wayNumber);
                }
            }
        }
        if (myRelation.getId() == null && (myRelation.getWayIds() == null || myRelation.getWayIds().isEmpty())) {
            throw new ParseException("Could not parse Way from line : " + l);
        }
        return myRelation;
    }

    private static Boolean checkIfNode(String[] tags, String[] line, int i) {
        return (tags[i].contains("nd ref=") && line.length > 0 && line[1].split("=").length > 0);
    }

    private static Boolean checkIfWay(String[] tags, String[] line, int i) {
        return (tags[i].contains("id=") && line.length > 0 && line[1].split("=").length > 0);
    }

    private static Boolean checkIfTag(String[] tags, String[] line, int i) {
        return (tags[i].contains("tag") && line.length > 2 && line[1].split("=").length > 0 && line[2].split("=")[1].length() > 0);
    }

    private static Boolean checkIfMember(String[] tags, String[] line, int i) {
        return (tags[i].contains("member") && line.length > 2 && line[1].split("=").length > 0 && line[2].split("=")[1].length() > 0);
    }
}
