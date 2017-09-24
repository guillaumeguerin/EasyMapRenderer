package maps.easyrenderer;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import database.SQLiteJDBC;
import exceptions.ParseException;
import model.Map;
import model.Node;
import model.Way;
import parser.NodeParser;

public class SQLiteJDBCTest {
	
	@Test
    public void testRetrieveMapFromDB() throws ParseException
    {
		boolean valid = false;
		Map myMap = SQLiteJDBC.retrieveMapFromDB(new Double(-85), new Double(85), new Double(-180), new Double(180), 15);
		List<Way> ways = myMap.getWays();
		for(int i=0; i<ways.size(); i++) {
			if(ways.get(i).getId().equals(new Double(30661813))) {
				if(ways.get(i).getNodes().size() == 11) {
					valid = true;
				}
			}
		}
		assertTrue(valid);
	}
}
