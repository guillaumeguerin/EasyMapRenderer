package maps.easyrenderer;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.Test;

import database.SQLiteJDBC;
import exceptions.DrawingException;
import exceptions.ParseException;
import model.Map;
import model.Node;
import model.Way;
import parser.NodeParser;
import parser.XMLParserFacade;

public class XMLParserFacadeTest {

	private static final String FILENAME = "C:\\Users\\admin\\eclipse-workspace\\easyrenderer\\smallerset.osm";
	
	@Test
    public void testParserWithInvalidString() throws ParseException
    {
		/*List <Object> osmList = new ArrayList<Object>();
		
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("smallerset.osm").getFile());
		StringBuilder result = new StringBuilder("");
		
		try (Scanner scanner = new Scanner(file)) {

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				Object o = new Object();
				o = XMLParserFacade.build(line);
				osmList.add(o);
			}

			scanner.close();

		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		boolean valid = false;
		
		BufferedReader br = null;
		FileReader fr = null;

		try {

			//br = new BufferedReader(new FileReader(FILENAME));
			fr = new FileReader(FILENAME);
			br = new BufferedReader(fr);

			String sCurrentLine;
			List<Object> osmList = new ArrayList<Object>();
			while ((sCurrentLine = br.readLine()) != null) {
				Object o = new Object();
				try {
					o = XMLParserFacade.build(sCurrentLine);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				osmList.add(o);
				
				for(int i = 0; i<osmList.size(); i++) {
					if(osmList.get(i) instanceof Way) {
						Way currentWay = (Way) osmList.get(i);
						if(currentWay.getId().equals(new Double(30661813))) {
							if(currentWay.getNodes().size() == 11) {
								valid = true;
							}
						}
					}
				}
			}
			
		}catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		

		assertTrue(valid);
    }
	
	
}
