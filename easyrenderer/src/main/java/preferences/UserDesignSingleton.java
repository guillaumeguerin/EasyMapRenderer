package preferences;

import gui.center.MapPreviewView;
import model.Member;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Loads the User design in JSON
 */
public class UserDesignSingleton {

    private HashMap<String, Boolean> checkedParameters = new HashMap<>();
    private HashMap<String, Color> typeToColorMap = new HashMap<>();
    private HashMap<String, String> typeToPolygonMap = new HashMap<>();
    private String minNode = "";
    private String maxNode = "";
    private int zoom = 1;
    private String jsonDesign = "default_style.json";
    private Set<Double> allWaysPossible = new HashSet();

    //Used by the Singleton
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

    public void loadProperties() {
        loadJsonProperties();
    }

    private void loadJsonProperties() {
        InputStream in = MapPreviewView.class.getClassLoader().getResourceAsStream(jsonDesign);
        try {
            if (!"default_style.json".equals(jsonDesign)) {
                File folder = new File(".");
                File[] listOfFiles = folder.listFiles();
                for (File file : listOfFiles) {
                    if (file.isFile()) {
                        if (file.getName().endsWith(jsonDesign)) {
                            in = new FileInputStream(file);
                        }
                    }
                }
            }
        } catch (Exception e) {

        }

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

    public String getMinNode() {
        return minNode;
    }

    public void setMinNode(String minNode) {
        this.minNode = minNode;
    }

    public String getMaxNode() {
        return maxNode;
    }

    public void setMaxNode(String maxNode) {
        this.maxNode = maxNode;
    }

    public int getZoom() {
        return zoom;
    }

    public void setZoom(int zoom) {
        this.zoom = zoom;
    }

    public String getJsonDesign() {
        return jsonDesign;
    }

    public void setJsonDesign(String jsonDesign) {
        this.jsonDesign = jsonDesign;
    }

    public Set<Double> getAllWaysPossible() {
        return allWaysPossible;
    }

    public void setAllWaysPossible(Set<Double> allWaysPossible) {
        this.allWaysPossible = allWaysPossible;
    }

    public void addWaysPossible(List<Member> memberList) {
        for (Member member : memberList) {
            this.allWaysPossible.add(member.getWayIsUsedByRelationId());
        }
    }
}
