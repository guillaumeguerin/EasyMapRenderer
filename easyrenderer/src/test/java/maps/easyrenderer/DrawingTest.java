package maps.easyrenderer;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import drawing.Drawing;
import model.Node;
import model.Map;

public class DrawingTest {

	@Test
    public void testcomputeXPosition()
    {
		List<Node> nodes = new ArrayList<Node>();
		nodes.add(new Node(1, 0, 0));
		nodes.add(new Node(2, 256, 256));
		Map map = new Map();
		map.setNodes(nodes);
		int xPos = Drawing.computeXPosition(map, new Node(-1, 0, 14));
		assertEquals(0, xPos);
    }

	@Test
    public void testcomputeYPosition()
    {
		List<Node> nodes = new ArrayList<Node>();
		nodes.add(new Node(1, 0, 0));
		nodes.add(new Node(2, 256, 256));
		Map map = new Map();
		map.setNodes(nodes);
		int yPos = Drawing.computeYPosition(map, new Node(-1, 0, 14));
		assertEquals(14, yPos);
    }
}
