package drawing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import exceptions.DrawingException;
import gui.center.MapOutputView;
import model.Map;
import model.Member;
import model.Node;
import model.Relation;
import model.Tag;
import model.Way;

public class MapDrawer {

	private static final Logger logger = Logger.getLogger(MapDrawer.class);
	
	/**
	 * Private constructor hiding the implicit public one.
	 */
	private MapDrawer() {
		// Empty on purpose.
	}
	
	public static void drawMap(Map m) {
        try {
        	BufferedImage bi = drawMapOnImage(m);
            ImageIO.write(bi, "PNG", new File("C:\\Users\\admin\\eclipse-workspace\\easyrenderer\\map.PNG"));
            
          } catch (IOException ie) {
        	  logger.error(ie);
          }
    }
	
	public static BufferedImage drawMapOnImage(Map m) {
		BufferedImage bi = new BufferedImage(Tile.width, Tile.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D image = bi.createGraphics();
       
        // TYPE_INT_ARGB specifies the image format: 8-bit RGBA packed
        // into integer pixels
        List<Way> ways = m.getWays();
        List<Relation> relations = m.getRelations();
        
        ways = addRelationTagsToWay(ways, relations);
        ways = reOrderWays(ways);
        
        image.setColor(getBackgroundColor());
        image.fillRect(0, 0, Tile.width, Tile.height);
                    
        for(int i=0; i<ways.size(); i++) {
        	Way currentWay = ways.get(i);
        	List<Node> idNodesList = currentWay.getNodes();
        
        	List<int[]> xyPoints = computeNodePosition(m, idNodesList);
        	int[] xPoints = xyPoints.get(0);
        	int[] yPoints = xyPoints.get(1);
        	
        	Color color = findColor(ways, i);
        	image.setColor(color);
        	
        	
        	if(isWayPolygon(currentWay)) {
        		image.fillPolygon(xPoints, yPoints, xPoints.length);
        	}
        	else {
        		List<Integer> xPoints2 = new ArrayList<>();
        		List<Integer> yPoints2 = new ArrayList<>();
        		List<Integer> xPoints3 = new ArrayList<>();
        		List<Integer> yPoints3 = new ArrayList<>();
        		
        		for(int j=0; j<xPoints.length -1; j++) {
        			xPoints3.add(xPoints[j]);
        			yPoints3.add(yPoints[j]);
        		}
        		int cpt=0;
        		while(xPoints3.size() > 0) {
        			Integer currentX = xPoints3.get(0);
        			Integer currentY = yPoints3.get(0);
        			if(cpt==0) {
        				xPoints2.add(currentX);
            			yPoints2.add(currentY);
            			xPoints3.remove(0);
            			yPoints3.remove(0);
        			}
        			
        			double min = -1;
        			for(int j=0; j<xPoints3.size(); j++) {
        				double dist = calculateDistanceBetweenPoints(currentX, currentY, xPoints3.get(j), yPoints3.get(j));
                		if(min == -1 || dist < min) {
                			min = dist;
                			xPoints2.add(xPoints3.get(j));
                			yPoints2.add(yPoints3.get(j));
                			xPoints3.remove(j);
                			yPoints3.remove(j);
                		}
        			}
        		}
        		
        		for(int j=0; j<xPoints.length -1; j++) {
        			xPoints[j] = xPoints2.get(j);
        			yPoints[j] = yPoints2.get(j);
        		}
        		            	
        		
        		for(int j=0; j<xPoints.length -2; j++) {
        			//TODO
        			//Reorder for line drawing to closest point from one another
        			image.drawLine(xPoints[j], yPoints[j], xPoints[j+1], yPoints[j+1]);
        		}
        	}
        	
        }


        //Font font = new Font("TimesRoman", Font.BOLD, 20);
        //ig2.setFont(font);
        /*String message = "www.java2s.com!";
        FontMetrics fontMetrics = ig2.getFontMetrics();
        int stringWidth = fontMetrics.stringWidth(message);
        int stringHeight = fontMetrics.getAscent();
        ig2.setPaint(Color.black);
        ig2.drawString(message, (width - stringWidth) / 2, height / 2 + stringHeight / 4);*/
        
        image.drawImage(bi, null, 0, 0);
        return bi;
    }
	
	public static boolean isWayPolygon(Way w) {
		List<Tag> tags = w.getTags();
		for(Tag tag : tags) {
			if(isTagOfType(tag, "path")) {
				return false;
			}
			else if(isTagOfType(tag, "footway")) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isTagOfType(Tag tag, String type) {
		return (tag.getType1().contains(type) || tag.getType2().contains(type));
	}
	
	public static boolean wayMachesType(Way w, String type) {
		type = type.toLowerCase();
		for(Tag t : w.getTags()) {
			if(/*type.equalsIgnoreCase(t.getType1()) || */type.equalsIgnoreCase(t.getType2())) {
				return true;
			}
		}
		return false;
	}
	
	public static double calculateDistanceBetweenPoints(int x1, int y1, int x2, int y2) {
		return Math.sqrt(Math.pow((x2-x1), 2)+ Math.pow((y2-y1), 2));
	}
	
	public static Color findColor(List<Way> ways, int i) {
		Color color = Color.WHITE;
		if(wayMachesType(ways.get(i), "Amenity")) {
			color = getAmenityColor();
    	}
		else if(wayMachesType(ways.get(i), "Barrier")) {
    		color = getBarrierColor();
    	}
		else if(wayMachesType(ways.get(i), "Border type")) {
    		color = getBorderTypeColor();
    	}
		else if(wayMachesType(ways.get(i), "Building")) {
    		color = getBuildingColor();
    	}
		else if(wayMachesType(ways.get(i), "Coastline")) {
    		color = getCoastlineColor();
    	}
		else if(wayMachesType(ways.get(i), "Cuisine")) {
    		color = getCuisineColor();
    	}
		else if(wayMachesType(ways.get(i), "Compacted")) {
    		color = getCompactedColor();
    	}
		else if(wayMachesType(ways.get(i), "Earth")) {
    		color = getEarthColor();
    	}
		else if(wayMachesType(ways.get(i), "Footway")) {
    		color = getFootwayColor();
    	}
		else if(wayMachesType(ways.get(i), "Forest")) {
    		color = getForestColor();
    	}
		else if(wayMachesType(ways.get(i), "Grass")) {
    		color = getGrassColor();
    	}
		else if(wayMachesType(ways.get(i), "Ground")) {
    		color = getGroundColor();
    	}
		else if(wayMachesType(ways.get(i), "Highway")) {
    		color = getHighwayColor();
    	}
		else if(wayMachesType(ways.get(i), "Historic")) {
    		color = getHistoricColor();
    	}
		else if(wayMachesType(ways.get(i), "Leisure")) {
    		color = getLeisureColor();
    	}
		else if(wayMachesType(ways.get(i), "Name")) {
    		color = getNameColor();
    	}
		else if(wayMachesType(ways.get(i), "Natural")) {
    		color = getNaturalColor();
    	}
		else if(wayMachesType(ways.get(i), "Neighbourhood")) {
    		color = getNeighbourhoodColor();
    	}
		else if(wayMachesType(ways.get(i), "Tourism")) {
    		color = getTourismColor();
    	}
		else if(wayMachesType(ways.get(i), "Parking")) {
    		color = getParkingColor();
    	}
		else if(wayMachesType(ways.get(i), "Path")) {
    		color = getPathColor();
    	}
		else if(wayMachesType(ways.get(i), "Recycling")) {
    		color = getRecyclingColor();
    	}
		else if(wayMachesType(ways.get(i), "Residential")) {
    		color = getResidentialColor();
    	}
		else if(wayMachesType(ways.get(i), "Sand")) {
    		color = getSandColor();
    	}
		else if(wayMachesType(ways.get(i), "Sport")) {
    		color = getSportColor();
    	}
		else if(wayMachesType(ways.get(i), "Wall")) {
    		color = getWallColor();
    	}
		else if(wayMachesType(ways.get(i), "Water")) {
    		color = getWaterColor();
    	}
		else if(wayMachesType(ways.get(i), "Waterway")) {
    		color = getWaterwayColor();
    	}
		else if(wayMachesType(ways.get(i), "Wood")) {
    		color = getWoodColor();
    	}
		else {
			List<Tag> tags = ways.get(i).getTags();
			for(Tag tag : tags) {
				System.out.println(tag.getType2());
			}
		}
    	return color;
	}
	
	public static Color getAmenityColor() {
		return new Color(114, 224, 116);
	}
	
	public static Color getBarrierColor() {
		return new Color(229, 222, 192);
	}
	public static Color getBorderTypeColor() {
		return new Color(135, 133, 127);
	}
	
	public static Color getBuildingColor() {
		return new Color(255, 244, 214);
	}
	public static Color getCoastlineColor() {
		return new Color(247, 230, 153);
	}
	public static Color getCompactedColor() {
		return new Color(81, 69, 55);
	}
	public static Color getCuisineColor() {
		return new Color(230, 249, 52);
	}
	public static Color getEarthColor() {
		return new Color(58, 31, 1);
	}
	public static Color getFootwayColor() {
		return new Color(132, 108, 79);
	}
	public static Color getForestColor() {
		return new Color(17, 89, 17);
	}
	public static Color getGrassColor() {
		return new Color(151, 229, 144);
	}
	public static Color getGroundColor() {
		return new Color(183, 118, 88);
	}
	public static Color getHighwayColor() {
		return new Color(204, 196, 177);
	}
	
	public static Color getHistoricColor() {
		return new Color(114, 224, 116);
	}
	public static Color getLeisureColor() {
		return new Color(114, 224, 116);
	}
	
	public static Color getNameColor() {
		return new Color(45, 44, 42);
	}
	public static Color getNaturalColor() {
		return new Color(114, 224, 116);
	}
	
	public static Color getNeighbourhoodColor() {
		return new Color(162, 216, 151);
	}
	public static Color getTourismColor() {
		return new Color(114, 224, 116);
	}
	public static Color getParkingColor() {
		return new Color(114, 224, 116);
	}
	public static Color getPathColor() {
		return new Color(229, 199, 162);
	}
	public static Color getRecyclingColor() {
		return new Color(88, 183, 67);
	}
	public static Color getResidentialColor() {
		return new Color(177, 204, 171);
	}
	public static Color getSandColor() {
		return new Color(255, 253, 178);
	}
	public static Color getSportColor() {
		return new Color(112, 206, 92);
	}
	public static Color getWallColor() {
		return new Color(230, 234, 237);
	}
	public static Color getWaterColor() {
		return new Color(110, 186, 244);
	}
	public static Color getWaterwayColor() {
		return new Color(110, 186, 244);
	}
	public static Color getWoodColor() {
		return new Color(78, 114, 75);
	}
	
	/**
	 * Adds relation tags to inner ways
	 * 
	 * @param ways
	 * @param relations
	 * @return
	 */
	public static List<Way> addRelationTagsToWay(List<Way> ways, List<Relation> relations) {
		for(int i=0; i<relations.size(); i++) {
			Relation currentRelation = relations.get(i);
			List<Member> members = currentRelation.getMembers();
			
			for(int j=0; j<members.size(); j++) {
				Member currentMember = members.get(j);
				if(currentMember.getRole().equals("outer")) {
					Double wayId = currentMember.getUsedBy();
					
					for(int k=0; k<ways.size(); k++) {
						if(ways.get(k).getId().equals(wayId)) {
							ways.get(k).addTags(currentRelation.getTags());
						}
					}
				}
			}
		}

        return ways;
	}
	
	/**
	 * Compute the nodes position
	 * @return a list of x,y coordinates
	 * @throws DrawingException 
	 */
	public static List<int[]> computeNodePosition(Map map, List<Node> idNodesList) {
		int[] xPoints = new int[idNodesList.size()];
    	int[] yPoints = new int[idNodesList.size()];
    	
    	if(!idNodesList.isEmpty()) {
    		idNodesList = Drawing.sortNodes(idNodesList);
    	}
    	
    	List<Point2D> pixelList = new ArrayList<>();
    	for(int j=0; j<idNodesList.size(); j++) {
    		Node currentNode = idNodesList.get(j);

    		Point2D point2D = getMapCoordinates(map, currentNode);
    		//point2D = rotate(point2D, 270);
    		point2D = enforcePointWithinMap(point2D);
    		
    		xPoints[j] = (int) point2D.getX();
    		yPoints[j] = (int) point2D.getY();
    		pixelList.add(point2D);
    	}
    	
    	pixelList = reorderPolygonNodesBeforeDrawing(pixelList);
    	
    	List<int[]> xyPoints = new ArrayList<>();
    	
    	for(int i=0; i<pixelList.size(); i++) {
    		xPoints[i] = (int) pixelList.get(i).getX();
    		//xPoints[i] = 255 - xPoints[i];
    		yPoints[i] = (int) pixelList.get(i).getY();
    	} 
    	
    	xyPoints.add(xPoints);
    	xyPoints.add(yPoints);
    	return xyPoints;
	}
	
	
	/**
	 * Gets the map coordinates for the node using its latitude / longitude
	 * 
	 * @param map
	 * @param currentNode
	 * @return
	 */
	public static Point2D getMapCoordinates(Map map, Node currentNode) {
		Double xPos = (currentNode.getLat() - map.getMinNode().getLat()) / map.getLatScale() * Tile.width;
		Double yPos = (currentNode.getLon() - map.getMinNode().getLon()) / map.getLonScale() * Tile.height;
		return new Point2D.Double(xPos.intValue(), yPos.intValue());
	}
	
	/**
	 * Rotates a point of the map
	 * 
	 * @param point
	 * @param degrees
	 * @return
	 */
	public static Point2D rotate(Point2D point, int degrees) {
		double[] pt = {point.getX(), point.getY()};
		AffineTransform.getRotateInstance(Math.toRadians(degrees), 0, 0)
		  .transform(pt, 0, pt, 0, 1); // specifying to use this double[] to hold coords
		Double xPos = pt[0];
		Double yPos = pt[1];
		xPos = Math.abs(xPos);
		yPos = Math.abs(yPos);
		
		return new Point2D.Double(xPos, yPos);
	}
	
	/**
	 * Makes sure the point is in the map range
	 * 
	 * @param point the node we draw on the map
	 * @return a point within the map boundaries
	 */
	public static Point2D enforcePointWithinMap(Point2D point) {
		Double xPos = point.getX();
		Double yPos = point.getY();
		if(xPos.intValue() > Tile.width) {
			xPos = (double) Tile.width;
		}
		if(yPos.intValue() > Tile.height) {
			yPos = (double) Tile.height;
		}
		if(xPos.intValue() < 0) {
			xPos = (double) 0;
		}
		if(yPos.intValue() < 0) {
			yPos = (double) 0;
		}
		return new Point2D.Double(xPos, yPos);
	}
	
	/**
	 * Reorders node in clockwise order before drawing
	 * 
	 * @param point2DList
	 * @return
	 */
	public static List<Point2D> reorderPolygonNodesBeforeDrawing(List<Point2D> point2DList) {
		List<Point2D> reorderedPoints = new ArrayList<>();
		if(point2DList.size() > 3 && point2DList.size() <= 6) {
	    	List<java.awt.Point> points = new ArrayList<>();
	    	for(int i=0; i<point2DList.size(); i++) {
	    		points.add(new java.awt.Point((int) point2DList.get(i).getX(), (int) point2DList.get(i).getY()));
	    	}
	    	try {
		    	List<java.awt.Point> convexHull = GrahamScan.getConvexHull(points);
		    	
		    	for(int i=0; i<point2DList.size(); i++) {
		    		if(i < convexHull.size()) {
		    			Point2D point2D = new Point2D.Double(convexHull.get(i).x, convexHull.get(i).y);
		    			reorderedPoints.add(point2D);
		    		}
		    	}
	    	} 
	    	catch(Exception e) {
	    		logger.error(e);
	    	}
    	}
		return reorderedPoints;
	}
	
	public static List<Way> reOrderWays(List<Way> ways) {
		List<Way> waterResult = new ArrayList<>();
		List<Way> otherResult = new ArrayList<>();
		
		for(int i=0;i<ways.size(); i++) {
			if(ways.get(i).hasSpecificTag("water")) {
				waterResult.add(ways.get(i));
			}
			else {
				otherResult.add(ways.get(i));
			}
		}
		waterResult.addAll(otherResult);
		
		return waterResult;
	}
	
	private static Color getBackgroundColor() {
		String colorValue = MapOutputView.BACKGROUND_COLOR;
		if(colorValue.equalsIgnoreCase("white")) {
			return new Color(255, 255, 255);
		}
		if(colorValue.equalsIgnoreCase("green")) {
			return new Color(185, 239, 158);
		}
		if(colorValue.equalsIgnoreCase("blue")) {
			return new Color(174, 223, 249);
		}
		else {
			return new Color(10, 10, 50);
		}
	}
}
