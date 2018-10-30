package gui.center;

import java.util.Map;

import javafx.scene.layout.HBox;

public class CenterView extends HBox {

	MapPreviewView mapPreviewView = new MapPreviewView();
	MapOutputView mapOutputView = new MapOutputView();
	MapParametersView mapParametersView = new MapParametersView();
	
	public CenterView() {
		super();
		this.setSpacing(15);
		this.getChildren().add(mapPreviewView);
		this.getChildren().add(mapOutputView);
		this.getChildren().add(mapParametersView);
	}
	
	public Map<String, Boolean> getParameters() {
		return this.mapParametersView.getCheckedParameters();
	}
}
