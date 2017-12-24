package gui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import database.FileParser;
import database.SQLiteJDBC;
import drawing.MapDrawer;
import exceptions.DrawingException;
import gui.bottom.BottomView;
import gui.center.CenterView;
import gui.center.MapOutputView;
import gui.center.MapParametersView;
import gui.center.MapPreviewView;
import gui.top.TopView;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Map;
import model.MapParameters;
import javafx.stage.FileChooser.ExtensionFilter;

public class AppGui {
	
	public static Stage buildPrimaryStage(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Easy Map Renderer");

        BorderPane componentLayout = new BorderPane();
        Insets insets = new Insets(20,0,20,20);
        componentLayout.setPadding(insets);

        componentLayout.setTop(new TopView(primaryStage));
        
        HBox listViews = new CenterView();
        componentLayout.setCenter(listViews);
        BorderPane.setMargin(listViews, insets);
        
        componentLayout.setBottom(new BottomView());
        
        //Add the BorderPane to the Scene
        Scene appScene = new Scene(componentLayout,700,400);
        
        //Add the Scene to the Stage
        primaryStage.setScene(appScene);
        primaryStage.show();
        return primaryStage;
	}

}
