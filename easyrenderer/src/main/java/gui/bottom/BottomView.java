package gui.bottom;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.HBox;

import java.io.File;
import java.util.Optional;

public class BottomView extends HBox {

    public BottomView() {
        this.setSpacing(10);

        Button startServerButton = new Button("Start server");
        startServerButton.setOnAction(value -> {

        });

        this.getChildren().add(startServerButton);


        //The button uses an inner class to handle the button click event
        Button deleteBut = new Button("Delete Database");
        deleteBut.setOnAction(value -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Database");
            alert.setHeaderText("Do you really want to delete the SQLlite database ?");
            alert.setContentText(computeDatabaseSize() + " kb of data will be deleted ");
            DialogPane dialogPane = alert.getDialogPane();
            //Styling application
            String css = BottomView.class.getResource("/gui/style.css").toExternalForm();
            dialogPane.getStylesheets().add(css);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                deleteDatabase();
            }
        });

        deleteBut.setStyle("-fx-background-color: #ff5a4d;-fx-border-color: #9d0d1b;-fx-text-fill: white;");
        this.getChildren().add(deleteBut);
    }

    private void deleteDatabase() {
        File folder = new File(".");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile() && listOfFiles[i].getName().endsWith(".db")) {
                listOfFiles[i].delete();
            }
        }
    }

    private double computeDatabaseSize() {
        File folder = new File(".");
        File[] listOfFiles = folder.listFiles();
        double bytes = 0;
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile() && listOfFiles[i].getName().endsWith(".db")) {
                bytes = listOfFiles[i].length();
            }
        }
        double kilobytes = (bytes / 1024);
        return kilobytes;
    }
}
