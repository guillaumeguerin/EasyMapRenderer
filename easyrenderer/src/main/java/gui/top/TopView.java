package gui.top;

import database.FileParser;
import database.SQLiteJDBC;
import drawing.MapDrawer;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.Map;

import java.io.File;

public class TopView extends HBox {

    public TopView(Stage primaryStage) {
        this.setSpacing(10);

        Label choiceLabel = new Label("OSM File : ");

        TextField dialogPath = new TextField();

        Button browseButton = new Button("Browse ...");

        browseButton.setOnAction(value -> {
            FileChooser fileChooser = new FileChooser();
            File workingDirectory = new File(System.getProperty("user.dir"));
            fileChooser.setInitialDirectory(workingDirectory);
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().addAll(new ExtensionFilter("OpenStreetMap data", "*.osm"));
            File chosenFile = fileChooser.showOpenDialog(primaryStage);
            if (chosenFile != null) {
                dialogPath.setText(chosenFile.getAbsolutePath());
                if (chosenFile.getAbsolutePath().endsWith(".osm")) {
                    FileParser.processOSMFile(chosenFile.getAbsolutePath());
                }
            }
        });

        Button resizeButton = new Button("Resize");
        resizeButton.setOnAction(value -> {

        });

        Button drawButton = new Button("Draw");
        drawButton.setOnAction(value -> {
            Map myMap = SQLiteJDBC.retrieveMapFromDB();
            MapDrawer.drawMap(myMap);
        });

        //Style
        choiceLabel.setStyle("-fx-padding: 5 0 0 0;");
        drawButton.setStyle("-fx-background-color: #75b1ff;-fx-border-color: #00299d;-fx-text-fill: white;");

        this.getChildren().add(choiceLabel);
        this.getChildren().add(dialogPath);
        this.getChildren().add(browseButton);
        this.getChildren().add(resizeButton);
        this.getChildren().add(drawButton);
    }
}
