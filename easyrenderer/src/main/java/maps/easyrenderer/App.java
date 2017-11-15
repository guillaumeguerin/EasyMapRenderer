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
import gui.AppGui;
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
	static String[] arguments;
	
	private static final String FILENAME = "C:\\Users\\admin\\eclipse-workspace\\easyrenderer\\island.osm";
	
    public static void main( String[] args )
    {
    	arguments = args;
    	launch(args);
    	//readFile();
    	
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		primaryStage = AppGui.buildPrimaryStage(primaryStage);
        primaryStage.show();
	}

}
