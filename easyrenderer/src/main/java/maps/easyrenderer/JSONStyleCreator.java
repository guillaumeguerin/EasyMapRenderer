package maps.easyrenderer;

import gui.center.MapPreviewView;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class JSONStyleCreator {

    public static void main(String[] args) throws Exception {
        InputStream in = MapPreviewView.class.getClassLoader().getResourceAsStream("default_style.json");
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String jsonContent = br.lines().collect(Collectors.joining());
        JSONArray arrayProperties = new JSONArray(jsonContent);
        for (int i = 0; i < arrayProperties.length(); i++) {
            JSONObject tagProperties = arrayProperties.getJSONObject(i);
            JSONArray colorRGBValues = tagProperties.getJSONArray("color");
            int color = (colorRGBValues.getInt(0) + colorRGBValues.getInt(1) + colorRGBValues.getInt(2)) / 3;
            colorRGBValues.put(0, color);
            colorRGBValues.put(1, color);
            colorRGBValues.put(2, color);
        }

        String path = JSONStyleCreator.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        // try-with-resources statement
        try (FileWriter file = new FileWriter(path + "/black_white.json")) {
            file.write(arrayProperties.toString());
        }
    }
}
