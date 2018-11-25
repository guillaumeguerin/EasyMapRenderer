package drawing;

import exceptions.DrawingException;
import gui.center.MapOutputView;
import model.*;
import org.apache.log4j.Logger;
import preferences.UserDesignSingleton;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class that draws the map to an image.
 */
public class MapDrawer {

    private static final Logger logger = Logger.getLogger(MapDrawer.class);

    /**
     * Private constructor hiding the implicit public one.
     */
    private MapDrawer() {
        // Empty on purpose.
    }

    /**
     * Draws the map and save it to a file
     *
     * @param map the map
     */
    public static void drawMap(Map map) {
        try {
            BufferedImage bi = drawMapOnImage(map);
            String filePath = System.getProperty("user.dir") + "\\map.PNG";
            ImageIO.write(bi, "PNG", new File(filePath));

        } catch (IOException ie) {
            logger.error(ie);
        }
    }

    /**
     * Draws the map
     *
     * @param map input map
     * @return an image
     */
    public static BufferedImage drawMapOnImage(Map map) {
        BufferedImage bi = new BufferedImage(Tile.width, Tile.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D image = bi.createGraphics();

        List<Way> ways = map.getWays();
        List<Relation> relations = map.getRelations();
        ways = addRelationTagsToWay(ways, relations);
        ways = reOrderWays(ways);

        image.setColor(getBackgroundColor());
        image.fillRect(0, 0, Tile.width, Tile.height);

        for (int i = 0; i < ways.size(); i++) {
            drawWayOnImage(image, map, ways, i);
        }


        //Font font = new Font("TimesRoman", Font.BOLD, 20);
        //ig2.setFont(font);
        /*String message = "www.java2s.com!";
        FontMetrics fontMetrics = ig2.getFontMetrics();
        int stringWidth = fontMetrics.stringWidth(message);
        int stringHeight = fontMetrics.getAscent();
        ig2.setPaint(Color.black);
        ig2.drawString(message, (width - stringWidth) / 2, height / 2 + stringHeight / 4);*/

        // Taken from https://stackoverflow.com/questions/8639567/java-rotating-images
        double rotationRequired = Math.toRadians(-90);
        double locationX = bi.getWidth() / 2;
        double locationY = bi.getHeight() / 2;
        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

        // Drawing the rotated image at the required drawing locations
        image.drawImage(op.filter(bi, null), 0, 0, null);

        return bi;
    }

    /**
     * Draws the way on the image
     *
     * @param image the image we are currently building
     * @param map   the map
     * @param ways  the ways to draw
     * @param index the index
     */
    public static void drawWayOnImage(Graphics2D image, Map map, List<Way> ways, int index) {
        Way currentWay = ways.get(index);
        if (!userWantsWayToBeDrawn(map, currentWay)) {
            return;
        }

        List<Node> idNodesList = currentWay.getNodes();

        List<int[]> xyPoints = computeNodePosition(map, idNodesList, isWayPolygon(map, currentWay));
        int[] xPoints = xyPoints.get(0);
        int[] yPoints = xyPoints.get(1);

        UserDesignSingleton designSingleton = UserDesignSingleton.getInstance();
        Color color = Color.WHITE;
        String currentType = ways.get(index).getTags().get(0).getType2();
        currentType = capitalizeString(currentType);
        if (!(ways.get(index).getTags().isEmpty()) && designSingleton.getTypeToColorMap() != null && designSingleton.getTypeToColorMap().containsKey(currentType)) {
            color = designSingleton.getTypeToColorMap().get(currentType);
        }

        image.setColor(color);

        if (isWayPolygon(map, currentWay)) {
            image.fillPolygon(xPoints, yPoints, xPoints.length);
        } else {
            for (int j = 0; j < xPoints.length - 2; j++) {
                image.drawLine(xPoints[j], yPoints[j], xPoints[j + 1], yPoints[j + 1]);
            }
        }
    }

    private static String capitalizeString(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * Does the user has checked the check box in the GUI ?
     *
     * @param m
     * @param w
     * @return
     */
    private static boolean userWantsWayToBeDrawn(Map m, Way w) {
        UserDesignSingleton designSingleton = UserDesignSingleton.getInstance();
        HashMap<String, Boolean> parameters = designSingleton.getCheckedParameters();
        if (parameters != null && w.getTags() != null && w.getTags().size() > 0 && w.getTags().get(0).getType2() != null) {
            String type = w.getTags().get(0).getType2();
            String cap = type.substring(0, 1).toUpperCase() + type.substring(1);
            if (parameters.get(cap) != null) {
                return parameters.get(cap);
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks the type of the way
     *
     * @param map the map
     * @param way the way
     * @return true if its a polygon
     */
    public static boolean isWayPolygon(Map map, Way way) {
        UserDesignSingleton designSingleton = UserDesignSingleton.getInstance();
        HashMap<String, String> polygonTypes = designSingleton.getTypeToPolygonMap();
        if (polygonTypes != null) {
            List<Tag> tags = way.getTags();
            for (Tag tag : tags) {
                if (polygonTypes.containsKey(tag.getType2())) {
                    return "polygon".equals(polygonTypes.get(tag.getType2()));
                }

            }
        }
        return false;
    }

    /**
     * Adds relation tags to inner ways
     *
     * @param ways
     * @param relations
     * @return
     */
    public static List<Way> addRelationTagsToWay(List<Way> ways, List<Relation> relations) {
        for (int i = 0; i < relations.size(); i++) {
            Relation currentRelation = relations.get(i);
            List<Member> members = currentRelation.getMembers();

            for (int j = 0; j < members.size(); j++) {
                Member currentMember = members.get(j);
                if (currentMember.getRole().equals("outer")) {
                    Double wayId = currentMember.getUsedBy();

                    for (int k = 0; k < ways.size(); k++) {
                        if (ways.get(k).getId().equals(wayId)) {
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
     *
     * @return a list of x,y coordinates
     * @throws DrawingException
     */
    public static List<int[]> computeNodePosition(Map map, List<Node> idNodesList, boolean isPolygon) {
        int[] xPoints = new int[idNodesList.size()];
        int[] yPoints = new int[idNodesList.size()];

        if (!idNodesList.isEmpty()) {
            idNodesList = Drawing.sortNodes(idNodesList);
        }

        List<Point2D> pixelList = new ArrayList<>();
        for (int j = 0; j < idNodesList.size(); j++) {
            Node currentNode = idNodesList.get(j);

            Point2D point2D = getMapCoordinates(map, currentNode);
            point2D = enforcePointWithinMap(point2D);

            xPoints[j] = (int) point2D.getX();
            yPoints[j] = (int) point2D.getY();
            pixelList.add(point2D);
        }

        if (isPolygon) {
            pixelList = reorderPolygonNodesBeforeDrawing(pixelList);
        }

        List<int[]> xyPoints = new ArrayList<>();

        for (int i = 0; i < pixelList.size(); i++) {
            xPoints[i] = (int) pixelList.get(i).getX();
            yPoints[i] = (int) pixelList.get(i).getY();
        }

        xyPoints.add(xPoints);
        xyPoints.add(yPoints);
        return xyPoints;
    }


    /**
     * Gets the map coordinates for the node using its latitude / longitude
     *
     * @param map         the osm map
     * @param currentNode the current node
     * @return a xy point to draw
     */
    public static Point2D getMapCoordinates(Map map, Node currentNode) {
        Double xPos = (currentNode.getLatitude() - map.getMinNode().getLatitude()) / map.getLatScale() * Tile.width;
        Double yPos = (currentNode.getLongitude() - map.getMinNode().getLongitude()) / map.getLonScale() * Tile.height;
        return new Point2D.Double(xPos.intValue(), yPos.intValue());
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
        if (xPos.intValue() > Tile.width) {
            xPos = (double) Tile.width;
        }
        if (yPos.intValue() > Tile.height) {
            yPos = (double) Tile.height;
        }
        if (xPos.intValue() < 0) {
            xPos = (double) 0;
        }
        if (yPos.intValue() < 0) {
            yPos = (double) 0;
        }
        return new Point2D.Double(xPos, yPos);
    }

    /**
     * Reorders node in clockwise order before drawing
     *
     * @param point2DList list of points to reorder
     * @return the sorted list of points
     */
    public static List<Point2D> reorderPolygonNodesBeforeDrawing(List<Point2D> point2DList) {
        List<Point2D> reorderedPoints = new ArrayList<>();
        if (point2DList.size() > 3 && point2DList.size() <= 6) {
            List<java.awt.Point> points = new ArrayList<>();
            for (int i = 0; i < point2DList.size(); i++) {
                points.add(new java.awt.Point((int) point2DList.get(i).getX(), (int) point2DList.get(i).getY()));
            }
            try {
                List<java.awt.Point> convexHull = GrahamScan.getConvexHull(points);

                for (int i = 0; i < point2DList.size(); i++) {
                    if (i < convexHull.size()) {
                        Point2D point2D = new Point2D.Double(convexHull.get(i).x, convexHull.get(i).y);
                        reorderedPoints.add(point2D);
                    }
                }
            } catch (Exception e) {
                //logger.error(e);
            }
        }
        return reorderedPoints;
    }

    /**
     * Re order ways. For example water is first to be drawn.
     *
     * @param ways the ways to re order
     * @return the sorted ways
     */
    public static List<Way> reOrderWays(List<Way> ways) {
        List<Way> waterResult = new ArrayList<>();
        List<Way> otherResult = new ArrayList<>();

        for (int i = 0; i < ways.size(); i++) {
            if (ways.get(i).hasSpecificTag("water")) {
                waterResult.add(ways.get(i));
            } else {
                otherResult.add(ways.get(i));
            }
        }
        waterResult.addAll(otherResult);

        return waterResult;
    }

    /**
     * Retrieve the background color selected by the user
     *
     * @return the selected color
     */
    private static Color getBackgroundColor() {
        String colorValue = MapOutputView.BACKGROUND_COLOR;
        if ("green".equalsIgnoreCase(colorValue)) {
            return new Color(185, 239, 158);
        } else if ("blue".equalsIgnoreCase(colorValue)) {
            return new Color(174, 223, 249);
        } else if ("black".equalsIgnoreCase(colorValue)) {
            return new Color(0, 0, 0);
        } else {
            return new Color(255, 255, 255);
            //return new Color(10, 10, 50);
        }
    }
}
