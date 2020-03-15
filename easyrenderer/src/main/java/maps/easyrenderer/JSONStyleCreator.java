package maps.easyrenderer;

import gui.center.MapPreviewView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class JSONStyleCreator {

    public static void main(String[] args) throws Exception {
        generateBlackAndWhiteTheme();
        generateDarkTheme();
        generateLightTheme();
    }

    private static JSONArray loadDefaultTheme() throws JSONException {
        InputStream in = MapPreviewView.class.getClassLoader().getResourceAsStream("default_style.json");
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String jsonContent = br.lines().collect(Collectors.joining());
        return new JSONArray(jsonContent);
    }

    private static void writeJsonToFile(String fileName, JSONArray arrayProperties) throws Exception {
        String path = JSONStyleCreator.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        // try-with-resources statement
        try (FileWriter file = new FileWriter(path + fileName)) {
            file.write(arrayProperties.toString());
        }
    }

    private static void generateBlackAndWhiteTheme() throws Exception {
        JSONArray arrayProperties = loadDefaultTheme();

        for (int i = 0; i < arrayProperties.length(); i++) {
            JSONObject tagProperties = arrayProperties.getJSONObject(i);
            JSONArray colorRGBValues = tagProperties.getJSONArray("color");
            int color = (colorRGBValues.getInt(0) + colorRGBValues.getInt(1) + colorRGBValues.getInt(2)) / 3;
            colorRGBValues.put(0, color);
            colorRGBValues.put(1, color);
            colorRGBValues.put(2, color);
        }

        writeJsonToFile("/black_white.json", arrayProperties);
    }

    private static void generateDarkTheme() throws Exception {
        JSONArray arrayProperties = loadDefaultTheme();

        for (int i = 0; i < arrayProperties.length(); i++) {
            JSONObject tagProperties = arrayProperties.getJSONObject(i);
            JSONArray colorRGBValues = tagProperties.getJSONArray("color");
            int color = (colorRGBValues.getInt(0) + colorRGBValues.getInt(1) + colorRGBValues.getInt(2)) / 3 / 4;
            colorRGBValues.put(0, color);
            colorRGBValues.put(1, color);
            colorRGBValues.put(2, color);
        }

        writeJsonToFile("/dark.json", arrayProperties);
    }

    private static void generateLightTheme() throws Exception {
        JSONArray arrayProperties = loadDefaultTheme();

        for (int i = 0; i < arrayProperties.length(); i++) {
            JSONObject tagProperties = arrayProperties.getJSONObject(i);
            JSONArray colorRGBValues = tagProperties.getJSONArray("color");
            int color = 255 - (colorRGBValues.getInt(0) + colorRGBValues.getInt(1) + colorRGBValues.getInt(2)) / 3 / 4;
            colorRGBValues.put(0, color);
            colorRGBValues.put(1, color);
            colorRGBValues.put(2, color);
        }

        writeJsonToFile("/light.json", arrayProperties);
    }
}
