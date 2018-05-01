package gui.center;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class MapPreviewView extends ImageView {

	public MapPreviewView() {
		super();
        this.setFitWidth(250);
        this.setFitHeight(250); 

        FileInputStream inputstream;
		try {
			inputstream = new FileInputStream("map.png");
			Image image = new Image(inputstream); 
	        this.setImage(image);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
