package gui.bottom;

import java.io.File;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class BottomView extends HBox {

	public BottomView() {
		this.setSpacing(10);
		
        /*Button startServerButton = new Button("Start server");
        startServerButton.setOnAction(value -> {
        	
        });
		
        this.getChildren().add(startServerButton);*/
		
		
		//The button uses an inner class to handle the button click event
        Button deleteBut = new Button("Delete Database");
        deleteBut.setOnAction(value -> {
        	File folder = new File(".");
        	File[] listOfFiles = folder.listFiles();

    	    for (int i = 0; i < listOfFiles.length; i++) {
    	      if (listOfFiles[i].isFile()) {
    	        //System.out.println("File " + listOfFiles[i].getName());
    	        if(listOfFiles[i].getName().endsWith(".db")) {
    	        	listOfFiles[i].delete();
    	        }
    	      } else if (listOfFiles[i].isDirectory()) {
    	        //System.out.println("Directory " + listOfFiles[i].getName());
    	      }
    	    }
        });
		
        this.getChildren().add(deleteBut);
	}
}
