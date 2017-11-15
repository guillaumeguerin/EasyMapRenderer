package gui;

import java.io.File;

import database.FileParser;
import database.SQLiteJDBC;
import drawing.MapDrawer;
import exceptions.DrawingException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Map;
import javafx.stage.FileChooser.ExtensionFilter;

public class AppGui {
	
	public static Stage buildPrimaryStage(Stage primaryStage) throws Exception {
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
        	if(chosenFile.getAbsolutePath().endsWith(".osm")) {
        		FileParser.readFile(chosenFile.getAbsolutePath());
        	}
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
        //listPane.getChildren().add(listLbl);
        //listPane.getChildren().add(vegetables);
        //listPane.setVisible(false);
        
        //The button uses an inner class to handle the button click event
        Button drawBut = new Button("Draw");
        drawBut.setOnAction(value -> {
        	Map myMap = SQLiteJDBC.retrieveMapFromDB(new Double(-85), new Double(85), new Double(-180), new Double(180), 15);
			try {
				MapDrawer.drawMap(myMap);
			} catch (DrawingException e) {
				e.printStackTrace();
			}
        });
        listPane.getChildren().add(drawBut);
        componentLayout.setCenter(listPane);
        
        //The button uses an inner class to handle the button click event
        Button deleteBut = new Button("Delete Database");
        deleteBut.setOnAction(value -> {
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
       
        
        componentLayout.setBottom(deleteBut);
        
        //Add the BorderPane to the Scene
        Scene appScene = new Scene(componentLayout,500,300);
        
        //Add the Scene to the Stage
        primaryStage.setScene(appScene);
        primaryStage.show();
        return primaryStage;
	}
}
