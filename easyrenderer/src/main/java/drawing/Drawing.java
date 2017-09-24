package drawing;

import java.util.ArrayList;
import java.util.List;

import model.Map;
import model.Node;

/**
 * Code taken from https://stackoverflow.com/questions/25287834/how-to-sort-a-collection-of-points-so-that-they-set-up-one-after-another
 * @author admin
 *
 */
public class Drawing {

	public static List<Node> sortNodes(List<Node> myList) {
		ArrayList<Node> orderedList = new ArrayList<Node>();

		orderedList.add(myList.remove(0)); //Arbitrary starting point

		while (myList.size() > 0) {
		   //Find the index of the closest point (using another method)
		   int nearestIndex=findNearestIndex(orderedList.get(orderedList.size()-1), myList);

		   //Remove from the unorderedList and add to the ordered one
		   orderedList.add(myList.remove(nearestIndex));
		}
		
		return orderedList;
	}
	
	//Note this is intentionally a simple algorithm, many faster options are out there
	public static int findNearestIndex (Node thisPoint, List<Node> listToSearch) {
	    double nearestDistSquared=Double.POSITIVE_INFINITY;
	    int nearestIndex = 0;
	    for (int i=0; i< listToSearch.size(); i++) {
	        Node point2=listToSearch.get(i);
	        Double distsq = (thisPoint.getLat() - point2.getLat())*(thisPoint.getLat() - point2.getLat()) 
	               + (thisPoint.getLon() - point2.getLon())*(thisPoint.getLon() - point2.getLon());
	        if(distsq < nearestDistSquared) {
	            nearestDistSquared = distsq;
	            nearestIndex=i;
	        }
	    }
	    return nearestIndex;
	}
	
	public List<Node> reorderNodes(List<Double> idNodesList, Map m) {
    	List<Node> nodesFound = new ArrayList<Node>();
    	for(int j=0; j<idNodesList.size(); j++) {
    		Node currentNode = m.retrieveNodeFromId(idNodesList.get(j));
    		nodesFound.add(currentNode);
    	}
    	return nodesFound;
	}
	
	public static int computeXPosition(Map m, Node n) {
		
		Double pos = Math.abs((n.getLat() - m.getMinNode().getLat()));
		pos = pos / m.getLatScale() * Tile.width;
		return pos.intValue();
	}
	
	public static int computeYPosition(Map m, Node n) {
		Double pos = Math.abs((n.getLon() - m.getMinNode().getLon())) / m.getLonScale() * Tile.height;
		return pos.intValue();
	}
}
