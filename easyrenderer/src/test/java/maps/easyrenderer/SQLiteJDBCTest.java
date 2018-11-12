package maps.easyrenderer;

import database.SQLiteJDBC;
import exceptions.ParseException;
import model.Map;
import model.Way;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class SQLiteJDBCTest {

    @Test
    public void testRetrieveMapFromDB() throws ParseException {
        boolean valid = false;
        Map myMap = SQLiteJDBC.retrieveMapFromDB();
        List<Way> ways = myMap.getWays();
        for (int i = 0; i < ways.size(); i++) {
            if (ways.get(i).getId().equals(new Double(30661813))) {
                if (ways.get(i).getNodes().size() == 11) {
                    valid = true;
                }
            }
        }
        assertTrue(valid);
    }
}
