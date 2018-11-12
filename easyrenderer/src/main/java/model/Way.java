package model;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Way implements Element {

    private static final Logger logger = Logger.getLogger(Way.class);

    private Double id;
    private Double usedBy;
    private List<Node> nodes = new ArrayList<>();
    private List<Tag> tags = new ArrayList<>();

    public Double getId() {
        return id;
    }

    public Double getUsedBy() {
        return usedBy;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setId(Double d) {
        id = d;
    }

    public void setUsedBy(Double d) {
        usedBy = d;
    }

    public void setNodes(List<Node> n) {
        nodes = n;
    }

    public void addNode(Node n) {
        nodes.add(n);
    }

    public void setTags(List<Tag> t) {
        tags = t;
    }

    public void addTag(Tag t) {
        tags.add(t);
    }

    public void addTags(List<Tag> t) {
        tags.addAll(t);
    }

    public boolean hasSpecificTag(String type) {
        type = type.toLowerCase();
        for (int i = 0; i < tags.size(); i++) {
            if (type.equalsIgnoreCase(tags.get(i).getType1()) || type.equalsIgnoreCase(tags.get(i).getType2())) {
                return true;
            }
        }
        return false;
    }

    public Way(String l) {
        setNodes(new ArrayList<Node>());
        String[] tags = l.split("<");
        for (int i = 0; i < tags.length; i++) {
            String[] line = tags[i].split(" ");
            if (tags[i].contains("nd ref=")) {
                if (line.length > 0 && line[1].split("=").length > 0) {
                    String nodeId = line[1].split("=")[1].replace("'", "");
                    try {
                        addNode(new Node(new Double(nodeId), 1., 1.));
                    } catch (Exception e) {
                        logger.debug("Node : " + nodeId);
                        logger.error(e);
                    }

                }
            } else if (tags[i].contains("id=")) {
                if (line.length > 0 && line[1].split("=").length > 0) {
                    String wayId = line[1].split("=")[1].replace("'", "");
                    try {
                        setId(Double.parseDouble(wayId));
                    } catch (Exception e) {
                        logger.debug("Way : " + wayId);
                        logger.error(e);
                    }

                }
            } else if (tags[i].contains("tag")) {
                if (line.length > 2 && line[1].split("=").length > 0 && line[2].split("=")[1].length() > 0) {
                    String type1 = line[1].split("=")[1].replace("'", "");
                    String type2 = line[2].split("=")[1].replace("'", "");
                    this.addTag(new Tag(1., 1., type1, type2));
                }
            }
        }
    }

    public Way(Double myId, Double usedBy, List<Node> nodes, List<Tag> tags) {
        setId(myId);
        setUsedBy(usedBy);
        setNodes(nodes);
        setTags(tags);
    }

    /**
     * Default constructor
     */
    public Way() {
        // Empty on purpose.
    }

}
