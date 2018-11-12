package maps.easyrenderer;

import gui.AppGui;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.log4j.PropertyConfigurator;

import java.util.Properties;

/**
 * Easy Renderer main class
 */
public class App extends Application {
    static String[] arguments;

    public static void main(String[] args) {
        arguments = args;
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Properties props = new Properties();
        props.load(getClass().getResourceAsStream("/log4j.properties"));
        PropertyConfigurator.configure(props);

        primaryStage = AppGui.buildPrimaryStage(primaryStage);
        primaryStage.show();
    }

}
