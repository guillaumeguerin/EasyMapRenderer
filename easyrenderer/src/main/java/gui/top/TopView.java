package gui.top;

import database.FileParser;
import database.SQLiteJDBC;
import drawing.MapDrawer;
import gui.center.CenterView;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.Map;

import java.io.File;
import java.util.HashMap;

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

        //The button uses an inner class to handle the button click event
        Button drawButton = new Button("Draw");
        drawButton.setOnAction(value -> {
            Map myMap = SQLiteJDBC.retrieveMapFromDB();

            MapDrawer.drawMap(myMap);

        });

        this.getChildren().add(choiceLabel);
        this.getChildren().add(dialogPath);
        this.getChildren().add(browseButton);
        this.getChildren().add(drawButton);
    }

    public HashMap<String, Boolean> getParameters() {
        HashMap<String, Boolean> parameters = new HashMap<>();
        ObservableList<Node> allElements = this.getParent().getChildrenUnmodifiable();
        for (int i = 0; i < allElements.size(); i++) {
            Node currentNode = allElements.get(i);
            if (currentNode instanceof CenterView) {
                CenterView centerView = (CenterView) currentNode;
                parameters = (HashMap<String, Boolean>) centerView.getParameters();
            }
        }
        return parameters;

    }
}
