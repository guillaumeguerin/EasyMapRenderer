package model;

import gui.center.MapOutputView;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Map {

    private static final Logger logger = Logger.getLogger(Map.class);

    private Node minNode;
    private Node maxNode;
    private HashMap<Integer, Node> nodes;
    private List<Way> ways;
    private List<Relation> relations;
    private HashMap<String, Boolean> typesToDrawParametersMap;

    public HashMap<String, Boolean> getTypesToDrawParametersMap() {
        return typesToDrawParametersMap;
    }

    public void setTypesToDrawParametersMap(HashMap<String, Boolean> map) {
        typesToDrawParametersMap = map;
    }

    public HashMap<Integer, Node> getNodes() {
        return nodes;
    }

    public List<Way> getWays() {
        return ways;
    }

    public List<Relation> getRelations() {
        return relations;
    }

    public void setMinNode(Node n) {
        minNode = n;
    }

    public void setMaxNode(Node n) {
        maxNode = n;
    }

    public Node getMinNode() {
        if (minNode == null || minNode.getLat() == null) {
            minNode = computeMinNode();
            try {
                String lat = MapOutputView.MIN_NODE.split(",")[0];
                String lon = MapOutputView.MIN_NODE.split(",")[1];
                minNode = new Node();
                minNode.setLat(Double.parseDouble(lat));
                minNode.setLon(Double.parseDouble(lon));
            } catch (Exception e) {
                logger.error(e);
            }
        }
        return minNode;
    }

    public Node getMaxNode() {
        if (maxNode == null) {
            maxNode = computeMaxNode();
            try {
                String lat = MapOutputView.MAX_NODE.split(",")[0];
                String lon = MapOutputView.MAX_NODE.split(",")[1];
                maxNode = new Node();
                maxNode.setLat(Double.parseDouble(lat));
                maxNode.setLon(Double.parseDouble(lon));
            } catch (Exception e) {
                logger.error(e);
            }
        }
        return maxNode;
    }

    public void setNodes(HashMap<Integer, Node> n) {
        nodes = n;
    }

    public void setNodes(List<Node> n) {
        HashMap<Integer, Node> nodesMap = new HashMap<>();
        for (int i = 0; i < n.size(); i++) {
            nodesMap.put(n.get(i).getId().intValue(), n.get(i));
        }
        nodes = nodesMap;
    }

    public void setWays(List<Way> w) {
        ways = w;
    }

    public void setRelations(List<Relation> r) {
        relations = r;
    }

    public Map(List<Node> n, List<Way> w, List<Relation> r) {
        setNodes(n);
        setWays(w);
        setRelations(r);
        setMinNode(computeMinNode(n));
        setMaxNode(computeMaxNode(n));
    }

    /**
     * Gets the minimum latitude and longitude and creates a new Node
     *
     * @param nodes list of nodes
     * @return
     */
    public Node computeMinNode(List<Node> nodes) {
        Double minLat = null;
        Double minLon = null;
        Node myNode = new Node();

        for (Node currentNode : nodes) {
            if ((minLat == null) || (minLat > currentNode.getLat())) {
                minLat = currentNode.getLat();
            }
            if ((minLon == null) || (minLon > currentNode.getLon())) {
                minLon = currentNode.getLon();
            }
        }

        myNode.setLat(minLat);
        myNode.setLon(minLon);
        return myNode;
    }

    public Node computeMinNode() {
        return computeMinNode(getAllMapNodes());
    }

    /**
     * Gets the maximum latitude and longitude and creates a new Node
     *
     * @param nodes list of nodes
     * @return
     */
    public Node computeMaxNode(List<Node> nodes) {
        Double maxLat = null;
        Double maxLon = null;
        Node myNode = new Node();

        for (Node currentNode : nodes) {
            if ((maxLat == null) || (maxLat < currentNode.getLat())) {
                maxLat = currentNode.getLat();
            }
            if ((maxLon == null) || (maxLon < currentNode.getLon())) {
                maxLon = currentNode.getLon();
            }
        }

        myNode.setLat(maxLat);
        myNode.setLon(maxLon);
        return myNode;
    }

    public Node computeMaxNode() {
        return computeMaxNode(getAllMapNodes());
    }

    public List<Node> getAllMapNodes() {
        List<Node> allNodes = new ArrayList<>();
        List<Way> mapWays = this.getWays();
        if (this.getWays() != null) {
            for (Way way : mapWays) {
                allNodes.addAll(way.getNodes());
            }
        }
        return allNodes;
    }

    public Map(HashMap<Integer, Node> n, List<Way> w) {
        setNodes(n);
        setWays(w);
    }

    /**
     * Default constructor.
     */
    public Map() {
        // Empty on purpose.
    }

    public Node retrieveNodeFromId(Double myId) {
        return nodes.get(myId.intValue());
    }

    public Double getLatScale() {
        return this.getMaxNode().getLat() - this.getMinNode().getLat();
    }

    public Double getLonScale() {
        return this.getMaxNode().getLon() - this.getMinNode().getLon();
    }

    public Boolean wayIsPresent(int id) {
        for (int i = 0; i < ways.size(); i++) {
            if (ways.get(i).getId().equals(new Double(id))) {
                return true;
            }
        }
        return false;
    }

    public Boolean nodeIsPresent(int id) {
        Node n = this.nodes.get(id);
        return n != null;
    }
}
