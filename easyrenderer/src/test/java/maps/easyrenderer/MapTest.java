package maps.easyrenderer;

import model.Map;
import model.Node;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MapTest {

    @Test
    public void testcomputeMinNodeWithNoElements() {
        List<Node> nodes = new ArrayList<Node>();
        Map map = new Map();
        map.setNodes(nodes);
        Node minNode = map.computeMinNode(nodes);
        assertNull(minNode.getLat());
    }

    @Test
    public void testcomputeMinNode() {
        List<Node> nodes = new ArrayList<Node>();
        Node n1 = new Node(new Double(1), new Double(-1), new Double(-1));
        Node n2 = new Node(new Double(2), new Double(10), new Double(-80));
        nodes.add(n1);
        nodes.add(n2);
        Map map = new Map();
        map.setNodes(nodes);
        Node minNode = map.computeMinNode(nodes);
        assertTrue(minNode.getLat().equals(new Double(-1)) && minNode.getLon().equals(new Double(-80)));
    }

    @Test
    public void testcomputeMaxNodeWithNoElements() {
        List<Node> nodes = new ArrayList<Node>();
        Map map = new Map();
        map.setNodes(nodes);
        Node maxNode = map.computeMaxNode(nodes);
        assertNull(maxNode.getLat());
    }

    @Test
    public void testcomputeMaxNode() {
        List<Node> nodes = new ArrayList<Node>();
        Node n1 = new Node(new Double(1), new Double(-1), new Double(-1));
        Node n2 = new Node(new Double(2), new Double(10), new Double(-80));
        nodes.add(n1);
        nodes.add(n2);
        Map map = new Map();
        map.setNodes(nodes);
        Node maxNode = map.computeMaxNode(nodes);
        assertTrue(maxNode.getLat().equals(new Double(10)) && maxNode.getLon().equals(new Double(-1)));
    }

    @Test
    public void testcomputeMinAndMaxNodeWithDifferentValues() {
        List<Node> nodes = new ArrayList<Node>();
        Node n1 = new Node(new Double(1), new Double(-1), new Double(-1));
        Node n2 = new Node(new Double(2), new Double(10), new Double(-80));
        nodes.add(n1);
        nodes.add(n2);
        Map map = new Map();
        map.setNodes(nodes);
        Node maxNode = map.computeMaxNode(nodes);
        Node minNode = map.computeMinNode(nodes);
        assertFalse(maxNode.equals(minNode));
    }

    @Test
    public void testcomputeMinAndMaxNodeWithSameValues() {
        List<Node> nodes = new ArrayList<Node>();
        Node n1 = new Node(new Double(1), new Double(-1), new Double(-1));
        Node n2 = new Node(new Double(2), new Double(-1), new Double(-1));
        nodes.add(n1);
        nodes.add(n2);
        Map map = new Map();
        map.setNodes(nodes);
        Node maxNode = map.computeMaxNode(nodes);
        Node minNode = map.computeMinNode(nodes);
        assertTrue(maxNode.equals(minNode));
    }
}
