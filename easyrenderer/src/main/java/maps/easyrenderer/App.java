package maps.easyrenderer;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.sun.javafx.scene.paint.GradientUtils.Parser;

import database.SQLiteJDBC;
import drawing.Drawing;
import drawing.Tile;
import exceptions.DrawingException;
import exceptions.ParseException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.Node;
import model.Map;
import model.Way;
import parser.XMLParserFacade;

/**
 * Hello world!
 *
 */
public class App extends Application
{
	private static final String FILENAME = "C:\\Users\\admin\\eclipse-workspace\\easyrenderer\\island.osm";
	
	public static void readFile() {
    	BufferedReader br = null;
		FileReader fr = null;

		try {

			//br = new BufferedReader(new FileReader(FILENAME));
			fr = new FileReader(FILENAME);
			br = new BufferedReader(fr);

			String sCurrentLine;
			List<Object> osmList = new ArrayList<Object>();
			while ((sCurrentLine = br.readLine()) != null) {
				Object o = new Object();
				try {
					o = XMLParserFacade.build(sCurrentLine);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				osmList.add(o);
			}
			/*if(!SQLiteJDBC.tableExists("NODE") && !SQLiteJDBC.tableExists("WAY")) {
				SQLiteJDBC.initTables();
			}*/
			
			//insertData(osmList);
			
			Map myMap = SQLiteJDBC.retrieveMapFromDB(new Double(-85), new Double(85), new Double(-180), new Double(180), 15);
			
			drawMap(myMap);
			
		} catch (IOException e) {

			e.printStackTrace();

		} catch (DrawingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
	}
	
    public static void main( String[] args )
    {
    	launch(args);
    	readFile();
    	
    }
    
    public static void insertData(List<Object> osmList) {
    	for(int i=0; i< osmList.size(); i++) {
			/*if(osmList.get(i) instanceof Node) {
				SQLiteJDBC.insertNode((Node) osmList.get(i));
			}
			else if(osmList.get(i) instanceof Way) {
				SQLiteJDBC.insertWay((Way) osmList.get(i));
			}*/
		}
		
		for(int i=0; i< osmList.size(); i++) {
			if(osmList.get(i) instanceof Way) {
				SQLiteJDBC.updateAllNodes((Way) osmList.get(i));
			}
		}
    }
    
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

	@Override
	public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Easy Map Renderer");

        BorderPane componentLayout = new BorderPane();
        componentLayout.setPadding(new Insets(20,0,20,20));
        
        final HBox choicePane = new HBox();
        choicePane.setSpacing(10);

        Label choiceLbl = new Label("OSM File : ");
        
        TextField dialogPath = new TextField ();
        
        Button browseBut = new Button("Browse ...");
        
        browseBut.setOnAction(value ->  {
        	FileChooser fileChooser = new FileChooser();
        	File workingDirectory = new File(System.getProperty("user.dir"));
        	fileChooser.setInitialDirectory(workingDirectory);
        	fileChooser.setTitle("Open Resource File");
        	fileChooser.getExtensionFilters().addAll(new ExtensionFilter("OpenStreetMap data", "*.osm"));
        	File chosenFile = fileChooser.showOpenDialog(primaryStage);
        	dialogPath.setText(chosenFile.getAbsolutePath());
         });
        
        choicePane.getChildren().add(choiceLbl);
        choicePane.getChildren().add(dialogPath);
        choicePane.getChildren().add(browseBut);
        
        //put the flowpane in the top area of the BorderPane
        componentLayout.setTop(choicePane);
        
        final VBox listPane = new VBox();
        //listPane.setHgap(100);
        Label listLbl = new Label("Vegetables");
        
        ListView vegetables = new ListView(FXCollections.observableArrayList("Apple", "Apricot", "Banana"
         ,"Cherry", "Date", "Kiwi", "Orange", "Pear", "Strawberry"));
        listPane.getChildren().add(listLbl);
        listPane.getChildren().add(vegetables);
        listPane.setVisible(false);
        
        componentLayout.setCenter(listPane);
        
        //The button uses an inner class to handle the button click event
        Button vegFruitBut = new Button("Delete Database");
        vegFruitBut.setOnAction(value -> {
        	File folder = new File(".");
        	File[] listOfFiles = folder.listFiles();

    	    for (int i = 0; i < listOfFiles.length; i++) {
    	      if (listOfFiles[i].isFile()) {
    	        System.out.println("File " + listOfFiles[i].getName());
    	        if(listOfFiles[i].getName().endsWith(".db")) {
    	        	listOfFiles[i].delete();
    	        }
    	      } else if (listOfFiles[i].isDirectory()) {
    	        System.out.println("Directory " + listOfFiles[i].getName());
    	      }
    	    }
        });
       
        
        componentLayout.setBottom(vegFruitBut);
        
        //Add the BorderPane to the Scene
        Scene appScene = new Scene(componentLayout,500,300);
        
        //Add the Scene to the Stage
        primaryStage.setScene(appScene);
        primaryStage.show();
	}
}
