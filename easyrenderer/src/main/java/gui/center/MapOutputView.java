package gui.center;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MapOutputView extends VBox {

    public static String MIN_NODE = "";
    public static String MAX_NODE = "";
    public static String BACKGROUND_COLOR = "";

    public MapOutputView() {
        super();
        this.setSpacing(2);

        ChoiceBox zoomListView = new ChoiceBox(FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16"));

        File folder = new File(".");
        File[] listOfFiles = folder.listFiles();

        List<String> jsonList = new ArrayList<>();
        jsonList.add("default_style.json");
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {

                if (listOfFiles[i].getName().endsWith(".json")) {
                    jsonList.add(listOfFiles[i].getName());
                }
            }
        }
        ObservableList cssObservableList = FXCollections.observableList(jsonList);
        ChoiceBox cssListView = new ChoiceBox(cssObservableList);

        ChoiceBox outputListView = new ChoiceBox(FXCollections.observableArrayList("PNG", "Heightmap"));

        ChoiceBox languageListView = new ChoiceBox(FXCollections.observableArrayList("English", "French"));

        ChoiceBox backgroundListView = new ChoiceBox(FXCollections.observableArrayList("White", "Black", "Blue", "Green"));
        backgroundListView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                BACKGROUND_COLOR = backgroundListView.getItems().get((Integer) number2).toString();
                updatePreview();
            }
        });

        zoomListView.getSelectionModel().selectFirst();
        cssListView.getSelectionModel().selectFirst();
        outputListView.getSelectionModel().selectFirst();
        languageListView.getSelectionModel().selectFirst();
        backgroundListView.getSelectionModel().selectFirst();

        TextField dialogMinNode = new TextField();
        dialogMinNode.textProperty().addListener((observable, oldValue, newValue) -> {
            MIN_NODE = newValue;
        });

        TextField dialogMaxNode = new TextField();
        dialogMaxNode.textProperty().addListener((observable, oldValue, newValue) -> {
            MAX_NODE = newValue;
        });

        this.getChildren().add(new Label("Zoom"));
        this.getChildren().add(zoomListView);
        this.getChildren().add(new Label("Style"));
        this.getChildren().add(cssListView);
        this.getChildren().add(new Label("Output"));
        this.getChildren().add(outputListView);
        this.getChildren().add(new Label("Language"));
        this.getChildren().add(languageListView);
        this.getChildren().add(new Label("Background"));
        this.getChildren().add(backgroundListView);
        this.getChildren().add(new Label("Min node"));
        this.getChildren().add(dialogMinNode);
        this.getChildren().add(new Label("Max node"));
        this.getChildren().add(dialogMaxNode);

    }


    private void updatePreview() {
        if (this.getParent() != null) {
            ObservableList<Node> guiNodes = this.getParent().getChildrenUnmodifiable();
            for (Node guiNode : guiNodes) {
                if (guiNode instanceof MapPreviewView) {
                    MapPreviewView guiPreview = (MapPreviewView) guiNode;
                    guiPreview.setImage(MapPreviewView.buildImage());
                }
            }
        }
    }
}
