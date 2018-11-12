package gui;

import gui.bottom.BottomView;
import gui.center.CenterView;
import gui.top.TopView;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AppGui {

    public static Stage buildPrimaryStage(Stage primaryStage) {
        primaryStage.setTitle("Easy Map Renderer");
        primaryStage.getIcons().add(new Image(AppGui.class.getResourceAsStream("icon.png")));

        BorderPane componentLayout = new BorderPane();
        Insets insets = new Insets(20, 0, 20, 20);
        componentLayout.setPadding(insets);

        componentLayout.setTop(new TopView(primaryStage));

        HBox listViews = new CenterView();
        componentLayout.setCenter(listViews);
        BorderPane.setMargin(listViews, insets);

        componentLayout.setBottom(new BottomView());

        //Add the BorderPane to the Scene
        Scene appScene = new Scene(componentLayout, 700, 500);

        //Add the Scene to the Stage
        primaryStage.setScene(appScene);
        primaryStage.show();
        return primaryStage;
    }

}
