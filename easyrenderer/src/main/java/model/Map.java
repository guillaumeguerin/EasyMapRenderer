package model;

import org.apache.log4j.Logger;
import preferences.UserDesignSingleton;

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
        if (minNode == null || minNode.getLatitude() == null) {
            minNode = computeMinNode();
            try {
                UserDesignSingleton designSingleton = UserDesignSingleton.getInstance();
                if (!designSingleton.getMinNode().isEmpty()) {
                    String[] latlonSplit = designSingleton.getMinNode().split(",");
                    if (latlonSplit.length > 1) {
                        String latitude = designSingleton.getMinNode().split(",")[0];
                        String longitude = designSingleton.getMinNode().split(",")[1];
                        minNode = new Node();
                        minNode.setLatitude(Double.parseDouble(latitude));
                        minNode.setLongitude(Double.parseDouble(longitude));
                    }
                }
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
                UserDesignSingleton designSingleton = UserDesignSingleton.getInstance();
                if (!designSingleton.getMaxNode().isEmpty()) {
                    String[] latlonSplit = designSingleton.getMaxNode().split(",");
                    if (latlonSplit.length > 1) {
                        String lat = designSingleton.getMaxNode().split(",")[0];
                        String lon = designSingleton.getMaxNode().split(",")[1];
                        maxNode = new Node();
                        maxNode.setLatitude(Double.parseDouble(lat));
                        maxNode.setLongitude(Double.parseDouble(lon));
                    }
                }
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
     * @return the minimum node
     */
    public Node computeMinNode(List<Node> nodes) {
        Double minLat = null;
        Double minLon = null;
        Node myNode = new Node();

        for (Node currentNode : nodes) {
            if ((minLat == null) || (minLat > currentNode.getLatitude())) {
                minLat = currentNode.getLatitude();
            }
            if ((minLon == null) || (minLon > currentNode.getLongitude())) {
                minLon = currentNode.getLongitude();
            }
        }

        myNode.setLatitude(minLat);
        myNode.setLongitude(minLon);
        return myNode;
    }

    public Node computeMinNode() {
        return computeMinNode(getAllMapNodes());
    }

    /**
     * Gets the maximum latitude and longitude and creates a new Node
     *
     * @param nodes list of nodes
     * @return the maximum node
     */
    public Node computeMaxNode(List<Node> nodes) {
        Double maxLat = null;
        Double maxLon = null;
        Node myNode = new Node();

        for (Node currentNode : nodes) {
            if ((maxLat == null) || (maxLat < currentNode.getLatitude())) {
                maxLat = currentNode.getLatitude();
            }
            if ((maxLon == null) || (maxLon < currentNode.getLongitude())) {
                maxLon = currentNode.getLongitude();
            }
        }

        myNode.setLatitude(maxLat);
        myNode.setLongitude(maxLon);
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

    public Double getLatScale() {
        return this.getMaxNode().getLatitude() - this.getMinNode().getLatitude();
    }

    public Double getLonScale() {
        return this.getMaxNode().getLongitude() - this.getMinNode().getLongitude();
    }

}
