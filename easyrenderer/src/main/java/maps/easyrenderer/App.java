package maps.easyrenderer;

import gui.AppGui;
import javafx.application.Application;

import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class App extends Application
{
	static String[] arguments;
		
    public static void main( String[] args )
    {
    	arguments = args;
    	launch(args);
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage = AppGui.buildPrimaryStage(primaryStage);
        primaryStage.show();
	}

}
