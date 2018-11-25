package gui.center;

import com.sun.javafx.scene.control.skin.LabeledText;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import preferences.UserDesignSingleton;

import java.util.HashMap;
import java.util.Set;

public class MapParametersView extends VBox {

    private UserDesignSingleton designSingleton;

    /**
     * Default constructor.
     */
    public MapParametersView() {
        super();
        ScrollPane s1 = new ScrollPane();
        s1.setPrefSize(120, 200);
        final VBox listCheckboxes = new VBox();
        listCheckboxes.setSpacing(10);
        listCheckboxes.setPadding(new Insets(5, 5, 5, 5));

        //Load checkboxes and set properties
        designSingleton = UserDesignSingleton.getInstance();
        Set<String> properties = designSingleton.getCheckedParameters().keySet();
        for (String property : properties) {
            listCheckboxes.getChildren().add(new CheckBox(property));
        }

        //Set all checkboxes to true
        for (int i = 0; i < listCheckboxes.getChildren().size(); i++) {
            Node currentNode = listCheckboxes.getChildren().get(i);
            if (currentNode instanceof CheckBox) {
                CheckBox currentCheckBox = (CheckBox) currentNode;
                currentCheckBox.setSelected(true);
            }
        }

        s1.setContent(listCheckboxes);

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


    public void readCheckBoxes(VBox vbox) {
        ObservableList<Node> vboxChildren = vbox.getChildren();
        HashMap<String, Boolean> checkedParameters = new HashMap<>();
        for (int k = 0; k < vboxChildren.size(); k++) {
            if (vboxChildren.get(k) instanceof CheckBox) {
                CheckBox currentCheckBox = (CheckBox) vboxChildren.get(k);
                LabeledText text = (LabeledText) currentCheckBox.getChildrenUnmodifiable().get(0);
                checkedParameters.put(text.getText(), currentCheckBox.isSelected());
            }
        }
        designSingleton.setCheckedParameters(checkedParameters);
    }

    public void readScrollPane(ScrollPane s) {
        VBox vbox = (VBox) s.getContent();
        readCheckBoxes(vbox);
    }


    public void setAllCheckBoxes(boolean value) {
        ScrollPane s = (ScrollPane) this.getChildren().get(0);
        VBox vbox = (VBox) s.getContent();
        ObservableList<Node> vboxChildren = vbox.getChildren();
        for (int k = 0; k < vboxChildren.size(); k++) {
            if (vboxChildren.get(k) instanceof CheckBox) {
                CheckBox currentCheckBox = (CheckBox) vboxChildren.get(k);
                currentCheckBox.setSelected(value);
            }
        }
    }
}
