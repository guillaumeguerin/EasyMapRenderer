package drawing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import exceptions.DrawingException;
import model.Map;
import model.MapParameters;
import model.Member;
import model.Node;
import model.Relation;
import model.Tag;
import model.Way;

public class MapDrawer {

	public static void drawMap(Map m, HashMap<String, Boolean> mapParameters) throws DrawingException {
        try {
            Double latScale = m.getLatScale();
            Double lonScale = m.getLonScale();
            
            // TYPE_INT_ARGB specifies the image format: 8-bit RGBA packed
            // into integer pixels
            BufferedImage bi = new BufferedImage(Tile.width, Tile.height, BufferedImage.TYPE_INT_ARGB);
            List<Way> ways = m.getWays();
            List<Relation> relations = m.getRelations();
            
            ways = addRelationTagsToWay(ways, relations);
            ways = reOrderWays(ways);
            
            Graphics2D image = bi.createGraphics();
            //image.setColor(new Color(112, 183, 224));
            image.setColor(new Color(10, 10, 50));
            image.fillRect(0, 0, Tile.width, Tile.height);
            
            Boolean foobar = false;
            
            for(int i=0; i<ways.size(); i++) {
            	Way currentWay = ways.get(i);
            	System.out.println(ways.get(i).getId());	 
            	if(currentWay.getId().equals(new Double(351196528))) {
            		System.out.println("");	 
            	}
            	List<Node> idNodesList = currentWay.getNodes();
            	
            	int[] xPoints = new int[idNodesList.size()];
            	int[] yPoints = new int[idNodesList.size()];
            	List<int[]> xyPoints = computeNodePosition(m, idNodesList);
            	xPoints = xyPoints.get(0);
            	yPoints = xyPoints.get(1);
            	
            	if(wayMachesType(ways.get(i), mapParameters, "Amenity")) {
            		image.setColor(getAmenityColor());
            		image.fillPolygon(xPoints, yPoints, xPoints.length);
            	}
            	if(wayMachesType(ways.get(i), mapParameters, "Barrier")) {
            		image.setColor(getBarrierColor());
            		image.fillPolygon(xPoints, yPoints, xPoints.length);
            	}
            	if(wayMachesType(ways.get(i), mapParameters, "Border type")) {
            		image.setColor(getBorderTypeColor());
            		image.fillPolygon(xPoints, yPoints, xPoints.length);
            	}
            	if(wayMachesType(ways.get(i), mapParameters, "Building")) {
            		image.setColor(getBuildingColor());
            		image.fillPolygon(xPoints, yPoints, xPoints.length);
            	}
            	if(wayMachesType(ways.get(i), mapParameters, "Coastline")) {
            		image.setColor(getCoastlineColor());
            		image.fillPolygon(xPoints, yPoints, xPoints.length);
            	}
            	if(wayMachesType(ways.get(i), mapParameters, "Cuisine")) {
            		image.setColor(getCuisineColor());
            		image.fillPolygon(xPoints, yPoints, xPoints.length);
            	}
            	if(wayMachesType(ways.get(i), mapParameters, "Grass")) {
            		image.setColor(getGrassColor());
            		image.fillPolygon(xPoints, yPoints, xPoints.length);
            	}
            	if(wayMachesType(ways.get(i), mapParameters, "Ground")) {
            		image.setColor(getGroundColor());
            		image.fillPolygon(xPoints, yPoints, xPoints.length);
            	}
            	if(wayMachesType(ways.get(i), mapParameters, "Highway")) {
            		image.setColor(getHighwayColor());
            		image.drawPolyline(xPoints, yPoints, xPoints.length);
            	}
            	if(wayMachesType(ways.get(i), mapParameters, "Historic")) {
            		image.setColor(getHistoricColor());
            		image.fillPolygon(xPoints, yPoints, xPoints.length);
            	}
            	if(wayMachesType(ways.get(i), mapParameters, "Leisure")) {
            		image.setColor(getLeisureColor());
            		image.fillPolygon(xPoints, yPoints, xPoints.length);
            	}
            	if(wayMachesType(ways.get(i), mapParameters, "Name")) {
            		image.setColor(getNameColor());
            		image.fillPolygon(xPoints, yPoints, xPoints.length);
            	}
            	if(wayMachesType(ways.get(i), mapParameters, "Natural")) {
            		image.setColor(getNaturalColor());
            		image.fillPolygon(xPoints, yPoints, xPoints.length);
            	}
            	if(wayMachesType(ways.get(i), mapParameters, "Neighbourhood")) {
            		image.setColor(getNeighbourhoodColor());
            		image.fillPolygon(xPoints, yPoints, xPoints.length);
            	}
            	if(wayMachesType(ways.get(i), mapParameters, "Tourism")) {
            		image.setColor(getTourismColor());
            		image.fillPolygon(xPoints, yPoints, xPoints.length);
            	}
            	if(wayMachesType(ways.get(i), mapParameters, "Parking")) {
            		image.setColor(getParkingColor());
            		image.fillPolygon(xPoints, yPoints, xPoints.length);
            	}
            	if(wayMachesType(ways.get(i), mapParameters, "Recycling")) {
            		image.setColor(getRecyclingColor());
            		image.fillPolygon(xPoints, yPoints, xPoints.length);
            	}
            	if(wayMachesType(ways.get(i), mapParameters, "Residential")) {
            		image.setColor(getResidentialColor());
            		image.fillPolygon(xPoints, yPoints, xPoints.length);
            	}
            	if(wayMachesType(ways.get(i), mapParameters, "Sand")) {
            		image.setColor(getSandColor());
            		image.fillPolygon(xPoints, yPoints, xPoints.length);
            	}
            	if(wayMachesType(ways.get(i), mapParameters, "Sport")) {
            		image.setColor(getSportColor());
            		image.fillPolygon(xPoints, yPoints, xPoints.length);
            	}
            	if(wayMachesType(ways.get(i), mapParameters, "Wall")) {
            		image.setColor(getWallColor());
            		image.fillPolygon(xPoints, yPoints, xPoints.length);
            	}
            	if(wayMachesType(ways.get(i), mapParameters, "Water")) {
            		image.setColor(getWaterColor());
            		image.fillPolygon(xPoints, yPoints, xPoints.length);
            	}
            	if(wayMachesType(ways.get(i), mapParameters, "Waterway")) {
            		image.setColor(getWaterwayColor());
            		image.fillPolygon(xPoints, yPoints, xPoints.length);
            	}
            	if(wayMachesType(ways.get(i), mapParameters, "Wood")) {
            		image.setColor(getWoodColor());
            		image.fillPolygon(xPoints, yPoints, xPoints.length);
            	}
            	//image.fillPolygon(xPoints, yPoints, xPoints.length);
            }


            //Font font = new Font("TimesRoman", Font.BOLD, 20);
            //ig2.setFont(font);
            /*String message = "www.java2s.com!";
            FontMetrics fontMetrics = ig2.getFontMetrics();
            int stringWidth = fontMetrics.stringWidth(message);
            int stringHeight = fontMetrics.getAscent();
            ig2.setPaint(Color.black);
            ig2.drawString(message, (width - stringWidth) / 2, height / 2 + stringHeight / 4);*/

            ImageIO.write(bi, "PNG", new File("C:\\Users\\admin\\eclipse-workspace\\easyrenderer\\map.PNG"));
            ImageIO.write(bi, "JPEG", new File("C:\\Users\\admin\\eclipse-workspace\\easyrenderer\\map.JPG"));
            ImageIO.write(bi, "gif", new File("C:\\Users\\admin\\eclipse-workspace\\easyrenderer\\map.GIF"));
            ImageIO.write(bi, "BMP", new File("C:\\Users\\admin\\eclipse-workspace\\easyrenderer\\map.BMP"));
            
          } catch (IOException ie) {
            ie.printStackTrace();
          }
    }
	
	public static boolean wayMachesType(Way w, HashMap<String, Boolean> mapParameters, String type) {
		type = type.toLowerCase();
		for(Tag t : w.getTags()) {
			if(type.equals(t.getType1().toLowerCase()) || type.equals(t.getType2().toLowerCase())) {
				return true;
			}
		}
		return false;
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
	
	public static Color getCuisineColor() {
		return new Color(230, 249, 52);
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
	
	public static boolean isRelationInnerWay(Relation r) {
		return false;
	}
	
	/**
	 * Compute the nodes position
	 * @return
	 * @throws DrawingException 
	 */
	public static List<int[]> computeNodePosition(Map m, List<Node> idNodesList) throws DrawingException {
		int[] xPoints = new int[idNodesList.size()];
    	int[] yPoints = new int[idNodesList.size()];
    	
    	List<Node> nodesFound = new ArrayList<Node>();
    	for(int j=0; j<idNodesList.size(); j++) {
    		Node currentNode = idNodesList.get(j);
    		nodesFound.add(currentNode);
    	}
    	if(nodesFound != null && nodesFound.size() > 0) {
    		nodesFound = Drawing.sortNodes(nodesFound);
    	}
    	for(int j=0; j<nodesFound.size(); j++) {
    		Node currentNode = nodesFound.get(j);
    		/*Double xPos = (currentNode.getLat() - m.getMinNode().getLat()) / m.getLatScale() * Tile.width;
    		Double yPos = (currentNode.getLon() - m.getMinNode().getLon()) / m.getLonScale() * Tile.height;*/
    		
    		/*Double xPos = (m.getMinNode().getLat() - currentNode.getLat()) / m.getLatScale() * Tile.width;
    		Double yPos = (m.getMinNode().getLon() - currentNode.getLon()) / m.getLonScale() * Tile.height;*/
    		
    		/*Double xPos = (currentNode.getLat() - m.getMaxNode().getLat()) / m.getLatScale() * Tile.width;
    		Double yPos = (currentNode.getLon() - m.getMaxNode().getLon()) / m.getLonScale() * Tile.height;*/
    		
    		/*Double xPos = (m.getMaxNode().getLat() - currentNode.getLat()) / m.getLatScale() * Tile.width;
    		Double yPos = (m.getMaxNode().getLon() - currentNode.getLon()) / m.getLonScale() * Tile.height;*/
    		
    		Double xPos = (m.getMaxNode().getLat() - currentNode.getLat()) / m.getLatScale() * Tile.width;
    		Double yPos = (m.getMaxNode().getLon() - currentNode.getLon()) / m.getLonScale() * Tile.height;
    		yPos -= Tile.height;
    		yPos = Math.abs(yPos);
    		
    		double[] pt = {xPos, yPos};
    		AffineTransform.getRotateInstance(Math.toRadians(90), 0, 0)
    		  .transform(pt, 0, pt, 0, 1); // specifying to use this double[] to hold coords
    		xPos = pt[0];
    		yPos = pt[1];
    		xPos = Math.abs(xPos);
    		yPos = Math.abs(yPos);
    		
    		if(xPos.intValue() > Tile.width) {
    			throw new DrawingException("Cannot draw item at x pos: " + xPos.intValue() + " as it is > to width: " + Tile.width);
    		}
    		if(yPos.intValue() > Tile.height) {
    			throw new DrawingException("Cannot draw item at y pos: " + yPos.intValue() + " as it is > to height: " + Tile.height);
    		}
    		
    		xPoints[j] = xPos.intValue();
    		yPoints[j] = yPos.intValue();
    		
    		if(currentNode.getId().equals(new Double(4217017613.))) {
    			System.out.println("");
    		}
    		
    	}
    	
    	if(xPoints.length >= 3 && xPoints.length <= 6) {
	    	List<java.awt.Point> points = new ArrayList<>();
	    	for(int i=0; i<xPoints.length; i++) {
	    		points.add(new java.awt.Point(xPoints[i],yPoints[i]));
	    	}
	    	List<java.awt.Point> convexHull = GrahamScan.getConvexHull(points);
	    	
	    	for(int i=0; i<xPoints.length; i++) {
	    		if(i < convexHull.size()) {
	    			xPoints[i] = convexHull.get(i).x;
		    		yPoints[i] = convexHull.get(i).y;
	    		}
	    	}
    	}
    	
    	List<int[]> xyPoints = new ArrayList<>();
    	xyPoints.add(xPoints);
    	xyPoints.add(yPoints);
    	return xyPoints;
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
}
