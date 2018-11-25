package preferences;

import gui.center.MapPreviewView;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Loads the User design in JSON
 */
public class UserDesignSingleton {

    private HashMap<String, Boolean> checkedParameters = new HashMap<>();
    private HashMap<String, Color> typeToColorMap = new HashMap<>();
    private HashMap<String, String> typeToPolygonMap = new HashMap<>();

    private static UserDesignSingleton instance;

    /**
     * Default constructor.
     */
    private UserDesignSingleton() {
        loadProperties();
    }

    public static UserDesignSingleton getInstance() {
        if (instance == null) {
            instance = new UserDesignSingleton();
        }

        return instance;
    }

    private void loadProperties() {
        InputStream in = MapPreviewView.class.getClassLoader().getResourceAsStream("default_style.json");
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String jsonContent = br.lines().collect(Collectors.joining());
        JSONArray arrayProperties = new JSONArray(jsonContent);
        for (int i = 0; i < arrayProperties.length(); i++) {
            JSONObject tagProperties = arrayProperties.getJSONObject(i);
            String tagName = tagProperties.getString("tag");
            checkedParameters.put(tagName, true);
            JSONArray colorRGBValues = tagProperties.getJSONArray("color");
            Color itemColor = new Color(colorRGBValues.getInt(0), colorRGBValues.getInt(1), colorRGBValues.getInt(2));
            typeToColorMap.put(tagName, itemColor);
            String polygon = tagProperties.getString("type");
            typeToPolygonMap.put(tagName, polygon);
        }
    }

    public HashMap<String, Boolean> getCheckedParameters() {
        return checkedParameters;
    }

    public void setCheckedParameters(HashMap<String, Boolean> checkedParameters) {
        this.checkedParameters = checkedParameters;
    }

    public HashMap<String, Color> getTypeToColorMap() {
        return typeToColorMap;
    }

    public void setTypeToColorMap(HashMap<String, Color> typeToColorMap) {
        this.typeToColorMap = typeToColorMap;
    }

    public HashMap<String, String> getTypeToPolygonMap() {
        return typeToPolygonMap;
    }

    public void setTypeToPolygonMap(HashMap<String, String> typeToPolygonMap) {
        this.typeToPolygonMap = typeToPolygonMap;
    }
}
