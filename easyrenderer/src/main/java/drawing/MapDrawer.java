package drawing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import exceptions.DrawingException;
import model.Map;
import model.Node;
import model.Way;

public class MapDrawer {

	public static void drawMap(Map m) throws DrawingException {
        try {
            Double latScale = m.getLatScale();
            Double lonScale = m.getLonScale();
            
            // TYPE_INT_ARGB specifies the image format: 8-bit RGBA packed
            // into integer pixels
            BufferedImage bi = new BufferedImage(Tile.width, Tile.height, BufferedImage.TYPE_INT_ARGB);
            List<Way> ways = m.getWays();
            Graphics2D ig2 = bi.createGraphics();
            ig2.setColor(new Color(112, 183, 224));
            ig2.fillRect(0, 0, Tile.width, Tile.height);
            
            Boolean foobar = false;
            
            for(int i=0; i<ways.size(); i++) {
            	Way currentWay = ways.get(i);
            	if(currentWay.getId().equals(new Double(32999216))) {
            		System.out.println("");	 
            	}
            	List<Double> idNodesList = currentWay.getNodes();
            	
            	int[] xPoints = new int[idNodesList.size()];
            	int[] yPoints = new int[idNodesList.size()];
            	
            	List<Node> nodesFound = new ArrayList<Node>();
            	for(int j=0; j<idNodesList.size(); j++) {
            		Node currentNode = m.retrieveNodeFromId(idNodesList.get(j));
            		nodesFound.add(currentNode);
            	}
            	nodesFound = Drawing.sortNodes(nodesFound);
            	for(int j=0; j<nodesFound.size(); j++) {
            		Node currentNode = nodesFound.get(j);
            		Double xPos = (currentNode.getLat() - m.getMinNode().getLat()) / m.getLatScale() * Tile.width;
            		Double yPos = (currentNode.getLon() - m.getMinNode().getLon()) / m.getLonScale() * Tile.height;
            		
            		if(xPos.intValue() > Tile.width) {
            			throw new DrawingException("Cannot draw item at x pos: " + xPos.intValue() + " as it is > to width: " + Tile.width);
            		}
            		if(yPos.intValue() > Tile.height) {
            			throw new DrawingException("Cannot draw item at y pos: " + yPos.intValue() + " as it is > to height: " + Tile.height);
            		}
            		
            		xPoints[j] = xPos.intValue();
            		yPoints[j] = yPos.intValue();
            		
            		if(nodesFound.get(j).getId().equals(new Double(371746006))) {
            			foobar = true;
            		}
            		
            	}
            	if(ways.get(i).getType1().equals("natural") && ways.get(i).getType2().equals("coastline") && foobar) {
            		ig2.setColor(new Color(114, 224, 116));
            		ig2.drawPolygon(xPoints, yPoints, xPoints.length);
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

            ImageIO.write(bi, "PNG", new File("C:\\Users\\admin\\eclipse-workspace\\easyrenderer\\yourImageName.PNG"));
            ImageIO.write(bi, "JPEG", new File("C:\\Users\\admin\\eclipse-workspace\\easyrenderer\\yourImageName.JPG"));
            ImageIO.write(bi, "gif", new File("C:\\Users\\admin\\eclipse-workspace\\easyrenderer\\yourImageName.GIF"));
            ImageIO.write(bi, "BMP", new File("C:\\Users\\admin\\eclipse-workspace\\easyrenderer\\yourImageName.BMP"));
            
          } catch (IOException ie) {
            ie.printStackTrace();
          }
    }
}
