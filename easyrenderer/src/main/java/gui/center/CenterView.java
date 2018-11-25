package gui.center;

import javafx.scene.layout.HBox;

public class CenterView extends HBox {

    MapPreviewView mapPreviewView = new MapPreviewView();
    MapOutputView mapOutputView = new MapOutputView();
    MapParametersView mapParametersView = new MapParametersView();

    /**
     * Default constructor.
     */
    public CenterView() {
        super();
        this.setSpacing(15);

        this.getChildren().add(mapPreviewView);
        this.getChildren().add(mapOutputView);
        this.getChildren().add(mapParametersView);
    }

}
