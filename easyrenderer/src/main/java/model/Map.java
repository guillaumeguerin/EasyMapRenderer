package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import gui.center.MapOutputView;

public class Map {

	Node minNode;
	Node maxNode;
	HashMap<Integer, Node> nodes;
	List<Way> ways;
	List<Relation> relations;
	
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
		if(minNode == null) {
			minNode = computeMinNode();
			try {
				//44.787726,-0.710559
				String lat = MapOutputView.MIN_NODE.split(",")[0];
				String lon = MapOutputView.MIN_NODE.split(",")[1];
				minNode = new Node();
				minNode.setLat(Double.parseDouble(lat));
				minNode.setLon(Double.parseDouble(lon));
			} catch (Exception e) {
				
			}
		}
		return minNode;
	}
	
	public Node getMaxNode() {
		if(maxNode == null) {
			maxNode = computeMaxNode();
			try {
				//44.783767,-0.702748
				String lat = MapOutputView.MAX_NODE.split(",")[0];
				String lon = MapOutputView.MAX_NODE.split(",")[1];
				maxNode = new Node();
				maxNode.setLat(Double.parseDouble(lat));
				maxNode.setLon(Double.parseDouble(lon));
			} catch (Exception e) {
				
			}
		}
		return maxNode;
	}
	
	public void setNodes(HashMap<Integer, Node> n) {
		nodes = n;
	}
	
	public void setNodes(List<Node> n) {
		HashMap<Integer, Node> nodesMap = new HashMap<Integer, Node>();
		for(int i=0; i<n.size(); i++) {
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
	 * @param n list of nodes
	 * @return
	 */
	public Node computeMinNode(List<Node> n) {
		Node myNode = null;
		if(n.size() > 0) {
			Node firstNode = n.get(0);
			myNode = new Node(firstNode.getId(), firstNode.getLat(), firstNode.getLon());
			for(int i=0; i<n.size(); i++) {
				if(n.get(i).getLat() < myNode.getLat()) {
					myNode.setLat(n.get(i).getLat());
				}
				if(n.get(i).getLon() < myNode.getLon()) {
					myNode.setLon(n.get(i).getLon());
				}
			}
		}
		return myNode;
	}
	
	public Node computeMinNode(HashMap<Integer, Node> h) {
		List<Node> nodeList = new ArrayList<Node>(h.values());
		return computeMinNode(nodeList);
	}
	
	public Node computeMinNode() {
		return computeMinNode(getAllMapNodes());
	}
	
	/**
	 * Gets the maximum latitude and longitude and creates a new Node
	 * @param n list of nodes
	 * @return
	 */
	public Node computeMaxNode(List<Node> n) {
		Node myNode = null;
		if(n.size() > 0) {
			/*Node firstNode = n.get(0);
			myNode = new Node(firstNode.getId(), firstNode.getLat(), firstNode.getLon());
			for(int i=0; i<n.size(); i++) {
				if(n.get(i).getLat() > myNode.getLat()) {
					myNode.setLat(n.get(i).getLat());
				}
				if(n.get(i).getLon() > myNode.getLon()) {
					myNode.setLon(n.get(i).getLon());
				}
			}*/
			Node firstNode = n.get(0);
			Double meanX = firstNode.getLat();
			Double meanY = firstNode.getLon();
			for(int i=0; i<n.size(); i++) {
				meanX += n.get(i).getLat();
				meanY += n.get(i).getLon();
			}
			meanX /= n.size();
			meanY /= n.size();
			myNode = new Node(firstNode.getId(), meanX, meanY);
		}
		return myNode;
	}
	
	public Node computeMaxNode(HashMap<Integer, Node> h) {
		List<Node> nodeList = new ArrayList<Node>(h.values());
		return computeMaxNode(nodeList);
	}
	
	public Node computeMaxNode() {
		return computeMaxNode(getAllMapNodes());
	}
	
	public List<Node> getAllMapNodes() {
		List<Node> allNodes = new ArrayList<Node>();
		List<Way> ways = this.getWays();
		if(this.getWays() != null) {
			for(Way way : ways) {
				allNodes.addAll(way.getNodes());
			}
		}
		return allNodes;
	}

	public Map(HashMap<Integer, Node> n, List<Way> w) {
		setNodes(n);
		setWays(w);
	}
	
	public Map() {
		// TODO Auto-generated constructor stub
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
		List<Way> ways = this.getWays();
		for(int i=0; i<ways.size(); i++) {
			if(ways.get(i).getId().equals(new Double(id))) {
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
