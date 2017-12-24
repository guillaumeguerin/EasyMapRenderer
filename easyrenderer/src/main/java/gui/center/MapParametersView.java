package gui.center;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.javafx.scene.control.skin.LabeledText;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class MapParametersView extends VBox {

	public Map<String, Boolean> checkedParameters = new HashMap<>();
	
	private static final String AMENITY = "Amenity";
	private static final String BARRIER = "Barrier";
	private static final String BORDER_TYPE = "Border type";
	private static final String BUILDING = "Building";
	private static final String COASTLINE = "Coastline";
	private static final String CUISINE = "Cuisine";
	private static final String GRASS = "Grass";
	private static final String GROUND = "Ground";
	private static final String HIGHWAY = "Highway";
	private static final String HISTORIC = "Historic";
	private static final String LEISURE = "Leisure";
	private static final String NAME = "Name";
	private static final String NATURAL = "Natural";
	private static final String NEIGHBOURHOOD = "Neighbourhood";
	private static final String TOURISM = "Tourism";
	private static final String PARKING = "Parking";
	private static final String PLACE = "Place";
	private static final String RECYCLING = "Recycling";
	private static final String RESIDENTIAL = "Residential";
	private static final String SAND = "Sand";
	private static final String SPORT = "Sport";
	private static final String WALL = "Wall";
	private static final String WATER = "Water";
	private static final String WATERWAY = "Waterway";
	private static final String WOOD = "Wood";
	
	public MapParametersView() {
		super();
        ScrollPane s1 = new ScrollPane();
        s1.setPrefSize(120, 200);
        final VBox listCheckboxes = new VBox();
        listCheckboxes.setSpacing(10);

        listCheckboxes.getChildren().add(new CheckBox(AMENITY));
        listCheckboxes.getChildren().add(new CheckBox(BARRIER));
        listCheckboxes.getChildren().add(new CheckBox(BORDER_TYPE));
        listCheckboxes.getChildren().add(new CheckBox(BUILDING));
        listCheckboxes.getChildren().add(new CheckBox(COASTLINE));
        listCheckboxes.getChildren().add(new CheckBox(CUISINE));
        listCheckboxes.getChildren().add(new CheckBox(GRASS));
        listCheckboxes.getChildren().add(new CheckBox(GROUND));
        listCheckboxes.getChildren().add(new CheckBox(HIGHWAY));
        listCheckboxes.getChildren().add(new CheckBox(HISTORIC));
        listCheckboxes.getChildren().add(new CheckBox(LEISURE));
        listCheckboxes.getChildren().add(new CheckBox(NAME));
        listCheckboxes.getChildren().add(new CheckBox(NATURAL));
        listCheckboxes.getChildren().add(new CheckBox(NEIGHBOURHOOD));
        listCheckboxes.getChildren().add(new CheckBox(TOURISM));
        listCheckboxes.getChildren().add(new CheckBox(PARKING));
        listCheckboxes.getChildren().add(new CheckBox(PLACE));
        listCheckboxes.getChildren().add(new CheckBox(RECYCLING));
        listCheckboxes.getChildren().add(new CheckBox(RESIDENTIAL));
        listCheckboxes.getChildren().add(new CheckBox(SAND));
        listCheckboxes.getChildren().add(new CheckBox(SPORT));
        listCheckboxes.getChildren().add(new CheckBox(WALL));
        listCheckboxes.getChildren().add(new CheckBox(WATER));
        listCheckboxes.getChildren().add(new CheckBox(WATERWAY));
        listCheckboxes.getChildren().add(new CheckBox(WOOD));
        
        s1.setContent(listCheckboxes);
        
        checkedParameters.put(AMENITY, false);
        checkedParameters.put(BARRIER, false);
        checkedParameters.put(BORDER_TYPE, false);
        checkedParameters.put(BUILDING, false);
        checkedParameters.put(COASTLINE, false);
        checkedParameters.put(CUISINE, false);
        checkedParameters.put(GRASS, false);
        checkedParameters.put(GROUND, false);
        checkedParameters.put(HIGHWAY, false);
        checkedParameters.put(HISTORIC, false);
        checkedParameters.put(LEISURE, false);
        checkedParameters.put(NAME, false);
        checkedParameters.put(NATURAL, false);
        checkedParameters.put(NEIGHBOURHOOD, false);
        checkedParameters.put(TOURISM, false);
        checkedParameters.put(PARKING, false);
        checkedParameters.put(PLACE, false);
        checkedParameters.put(RECYCLING, false);
        checkedParameters.put(RESIDENTIAL, false);
        checkedParameters.put(SPORT, false);
        checkedParameters.put(SAND, false);
        checkedParameters.put(WALL, false);
        checkedParameters.put(WATER, false);
        checkedParameters.put(WATERWAY, false);
        checkedParameters.put(WOOD, false);
        
        Button checkButton = new Button("Select All");
        checkButton.setOnAction(value -> {
        	setAllCheckBoxes(true);
        });
        Button uncheckButton = new Button("Unselect All");
        uncheckButton.setOnAction(value -> {
        	setAllCheckBoxes(false);
        });
        
        this.setSpacing(10);
        this.getChildren().add(s1);
        this.getChildren().add(checkButton);
        this.getChildren().add(uncheckButton);
	}
	
	public Map<String, Boolean> getCheckedParameters() {
		ObservableList<Node> rootChildren = this.getChildren();
		for(int i=0; i<rootChildren.size(); i++) {
			if(rootChildren.get(i) instanceof ScrollPane) {
				ScrollPane s = (ScrollPane) rootChildren.get(i);
				readScrollPane(s);
			}
		}
		return checkedParameters;
	}
	
	public void readCheckBoxes(VBox vbox) {
		ObservableList<Node> vboxChildren = vbox.getChildren();
		for(int k=0; k<vboxChildren.size(); k++) {
			if(vboxChildren.get(k) instanceof CheckBox) {
				CheckBox currentCheckBox = (CheckBox) vboxChildren.get(k);
				LabeledText text = (LabeledText) currentCheckBox.getChildrenUnmodifiable().get(0);
				checkedParameters.put(text.getText(), currentCheckBox.isSelected());
			}
		}
	}
	
	public void readScrollPane(ScrollPane s) {
		VBox vbox = (VBox) s.getContent();
		readCheckBoxes(vbox);
	}
	
	
	public void setAllCheckBoxes(boolean value) {
		ScrollPane s = (ScrollPane) this.getChildren().get(0);
		VBox vbox = (VBox) s.getContent();
		ObservableList<Node> vboxChildren = vbox.getChildren();
		for(int k=0; k<vboxChildren.size(); k++) {
			if(vboxChildren.get(k) instanceof CheckBox) {
				CheckBox currentCheckBox = (CheckBox) vboxChildren.get(k);
				currentCheckBox.setSelected(value);
			}
		}
	}
}
