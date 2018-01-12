package model;

import java.util.ArrayList;
import java.util.List;

public class Way implements Element {

	Double id;
	Double usedBy;
	List<Node> nodes = new ArrayList<Node>();
	List<Tag> tags = new ArrayList<Tag>();
	
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
	
	public Way(String l) {
		setNodes(new ArrayList<Node>());
		String[] tags = l.split("<");
		for(int i=0; i<tags.length; i++) {
			String[] line = tags[i].split(" "); 
			if(tags[i].contains("nd ref=")) {
				if(line.length > 0 && line[1].split("=").length > 0) {
					String nodeId = line[1].split("=")[1].replace("'", "");
					try {
						addNode(new Node(new Double(nodeId), 1., 1.));
					}
					catch(Exception e) {
						System.out.println(nodeId + e.getMessage());
					}

				}
			}
			else if(tags[i].contains("id=")) {
				if(line.length > 0 && line[1].split("=").length > 0) {
					String wayId = line[1].split("=")[1].replace("'", "");
					try {
						setId(Double.parseDouble(wayId));
					}
					catch(Exception e) {
						System.out.println(wayId + e.getMessage());
					}

				}
			}
			else if(tags[i].contains("tag")) {
				if(line.length > 2 && line[1].split("=").length > 0 && line[2].split("=")[1].length() > 0) {
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

	public Way() {
		// TODO Auto-generated constructor stub
	}

	public Way(double id) {
		setId(id);
	}
}
