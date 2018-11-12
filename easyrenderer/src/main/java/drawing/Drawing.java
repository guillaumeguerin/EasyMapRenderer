package drawing;

import model.Map;
import model.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Code taken from https://stackoverflow.com/questions/25287834/how-to-sort-a-collection-of-points-so-that-they-set-up-one-after-another
 *
 * @author admin
 */
public class Drawing {

    public static List<Node> sortNodes(List<Node> myList) {
        ArrayList<Node> orderedList = new ArrayList<>();

        orderedList.add(myList.remove(0)); //Arbitrary starting point

        while (!myList.isEmpty()) {
            //Find the index of the closest point (using another method)
            int nearestIndex = findNearestIndex(orderedList.get(orderedList.size() - 1), myList);

            //Remove from the unorderedList and add to the ordered one
            orderedList.add(myList.remove(nearestIndex));
        }

        return orderedList;
    }

    //Note this is intentionally a simple algorithm, many faster options are out there
    public static int findNearestIndex(Node thisPoint, List<Node> listToSearch) {
        double nearestDistSquared = Double.POSITIVE_INFINITY;
        int nearestIndex = 0;
        for (int i = 0; i < listToSearch.size(); i++) {
            Node point2 = listToSearch.get(i);
            Double distsq = (thisPoint.getLatitude() - point2.getLatitude()) * (thisPoint.getLatitude() - point2.getLatitude())
                    + (thisPoint.getLongitude() - point2.getLongitude()) * (thisPoint.getLongitude() - point2.getLongitude());
            if (distsq < nearestDistSquared) {
                nearestDistSquared = distsq;
                nearestIndex = i;
            }
        }
        return nearestIndex;
    }

    public static int computeXPosition(Map m, Node n) {

        Double pos = Math.abs((n.getLatitude() - m.getMinNode().getLatitude()));
        pos = pos / m.getLatScale() * Tile.width;
        return pos.intValue();
    }

    public static int computeYPosition(Map m, Node n) {
        Double pos = Math.abs((n.getLongitude() - m.getMinNode().getLongitude())) / m.getLonScale() * Tile.height;
        return pos.intValue();
    }
}