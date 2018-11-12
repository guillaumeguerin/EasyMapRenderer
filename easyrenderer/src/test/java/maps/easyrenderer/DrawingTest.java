package maps.easyrenderer;

import drawing.Drawing;
import model.Map;
import model.Node;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DrawingTest {

    @Test
    public void testcomputeXPosition() {
        List<Node> nodes = new ArrayList<Node>();
        Node minNode = new Node(1, 0, 0);
        Node maxNode = new Node(2, 256, 256);
        nodes.add(minNode);
        nodes.add(maxNode);
        Map map = new Map();
        map.setNodes(nodes);
        map.setMinNode(minNode);
        map.setMaxNode(maxNode);
        int xPos = Drawing.computeXPosition(map, new Node(-1, 0, 14));
        assertEquals(0, xPos);
    }

    @Test
    public void testcomputeYPosition() {
        List<Node> nodes = new ArrayList<Node>();
        Node minNode = new Node(1, 0, 0);
        Node maxNode = new Node(2, 256, 256);
        nodes.add(minNode);
        nodes.add(maxNode);
        Map map = new Map();
        map.setNodes(nodes);
        map.setMinNode(minNode);
        map.setMaxNode(maxNode);
        int yPos = Drawing.computeYPosition(map, new Node(-1, 0, 14));
        assertEquals(14, yPos);
    }
}
