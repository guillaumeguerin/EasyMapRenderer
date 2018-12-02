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
import preferences.UserDesignSingleton;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MapOutputView extends VBox {

    public static String BACKGROUND_COLOR = "";

    public MapOutputView() {
        super();
        this.setSpacing(2);

        //Zoom
        ChoiceBox zoomListView = new ChoiceBox(FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16"));
        zoomListView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                UserDesignSingleton designSingleton = UserDesignSingleton.getInstance();
                designSingleton.setZoom(Integer.parseInt(zoomListView.getItems().get((Integer) number2).toString()));
                designSingleton.loadProperties();
                updatePreview();
            }
        });

        //Design
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
        ObservableList jsonObservableList = FXCollections.observableList(jsonList);
        ChoiceBox jsonListView = new ChoiceBox(jsonObservableList);
        jsonListView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                UserDesignSingleton designSingleton = UserDesignSingleton.getInstance();
                designSingleton.setJsonDesign(jsonListView.getItems().get((Integer) number2).toString());
                designSingleton.loadProperties();
                updatePreview();
            }
        });

        //Output
        ChoiceBox outputListView = new ChoiceBox(FXCollections.observableArrayList("PNG", "Heightmap"));

        //Language
        ChoiceBox languageListView = new ChoiceBox(FXCollections.observableArrayList("None", "English", "French"));

        //Background
        ChoiceBox backgroundListView = new ChoiceBox(FXCollections.observableArrayList("White", "Black", "Blue", "Green", "Dark", "Light"));
        backgroundListView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                BACKGROUND_COLOR = backgroundListView.getItems().get((Integer) number2).toString();
                updatePreview();
            }
        });

        //Set first element for all choiceboxes
        zoomListView.getSelectionModel().selectFirst();
        jsonListView.getSelectionModel().selectFirst();
        outputListView.getSelectionModel().selectFirst();
        languageListView.getSelectionModel().selectFirst();
        backgroundListView.getSelectionModel().selectFirst();

        //Boundaries
        TextField dialogMinNode = new TextField();
        dialogMinNode.textProperty().addListener((observable, oldValue, newValue) -> {
            UserDesignSingleton designSingleton = UserDesignSingleton.getInstance();
            designSingleton.setMinNode(newValue);
        });

        TextField dialogMaxNode = new TextField();
        dialogMaxNode.textProperty().addListener((observable, oldValue, newValue) -> {
            UserDesignSingleton designSingleton = UserDesignSingleton.getInstance();
            designSingleton.setMaxNode(newValue);
        });

        this.getChildren().add(new Label("Zoom"));
        this.getChildren().add(zoomListView);
        this.getChildren().add(new Label("Style"));
        this.getChildren().add(jsonListView);
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
