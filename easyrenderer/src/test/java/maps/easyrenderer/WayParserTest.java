package maps.easyrenderer;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.junit.Test;


import exceptions.ParseException;
import parser.NodeParser;
import parser.WayParser;



public class WayParserTest {

	@Test(expected = ParseException.class)
    public void testParserWithInvalidString() throws ParseException
    {
		WayParser.parse("<foo");
    }
	
	@Test(expected = ParseException.class)
    public void testParserWithInvalidXml() throws ParseException
    {
		WayParser.parse("<foo");
    }
	
	
	/*@Test(expected = ParseException.class)
    public void testParserResource() throws ParseException
    {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("smallerset.osm").getFile());
		StringBuilder result = new StringBuilder("");
		
		try (Scanner scanner = new Scanner(file)) {

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				result.append(line).append("\n");
			}

			scanner.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		
  		WayParser.parse("<foo");
    }*/
	
}
