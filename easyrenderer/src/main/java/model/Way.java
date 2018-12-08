package model;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Way implements Element {

    private static final Logger logger = Logger.getLogger(Way.class);

    private Double id;
    private Double relationId;
    private List<Node> nodes = new ArrayList<>();
    private List<Tag> tags = new ArrayList<>();

    public Double getId() {
        return id;
    }

    public Double getRelationId() {
        return relationId;
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

    public void setRelationId(Double id) {
        relationId = id;
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

    public Way(Double myId, Double relationId, List<Node> nodes, List<Tag> tags) {
        setId(myId);
        setRelationId(relationId);
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
