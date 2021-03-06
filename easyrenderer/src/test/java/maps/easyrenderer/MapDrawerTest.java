package maps.easyrenderer;

import java.awt.geom.Point2D;

import org.junit.Test;
import static org.assertj.core.api.Assertions.*;
import drawing.MapDrawer;
import model.Map;
import model.Node;

public class MapDrawerTest {

	@Test
	public void getMapCoordinates_should_return_0_0() {
		Map map = new Map();
		Node minNode = new Node();
		minNode.setLatitude(0.);
		minNode.setLongitude(0.);
		Node maxNode = new Node();
		maxNode.setLatitude(1000000000.);
		maxNode.setLongitude(1000000000.);
		map.setMinNode(minNode);
		map.setMaxNode(maxNode);
		Node node = new Node();
		node.setLatitude(1.);
		node.setLongitude(1.);
		
		Point2D expectedPoint = new Point2D.Double(0,0);
		Point2D resultPoint = MapDrawer.getMapCoordinates(map, node);
		assertThat(resultPoint).isEqualTo(expectedPoint);
	}
	
	@Test
	public void getMapCoordinates_should_return_255_255() {
		Map map = new Map();
		Node minNode = new Node();
		minNode.setLatitude(0.);
		minNode.setLongitude(0.);
		Node maxNode = new Node();
		maxNode.setLatitude(1000000000.);
		maxNode.setLongitude(1000000000.);
		map.setMinNode(minNode);
		map.setMaxNode(maxNode);
		Node node = new Node();
		node.setLatitude(999999999.);
		node.setLongitude(999999999.);
		
		Point2D expectedPoint = new Point2D.Double(255,255);
		Point2D resultPoint = MapDrawer.getMapCoordinates(map, node);
		assertThat(resultPoint).isEqualTo(expectedPoint);
	}
	
	@Test
	public void getMapCoordinates_should_return_X_X() {
		Map map = new Map();
		Node minNode = new Node();
		minNode.setLatitude(44.7836423);
		minNode.setLongitude(-0.7100189);
		Node maxNode = new Node();
		maxNode.setLatitude(44.98571196875001);
		maxNode.setLongitude(-0.7063329);
		map.setMinNode(minNode);
		map.setMaxNode(maxNode);
		Node node = new Node();
		node.setLatitude(44.7865032);
		node.setLongitude(-0.7063329);
		
		Point2D expectedPoint = new Point2D.Double(3,256);
		Point2D resultPoint = MapDrawer.getMapCoordinates(map, node);
		assertThat(resultPoint).isEqualTo(expectedPoint);
	}
}
