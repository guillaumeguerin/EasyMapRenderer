package maps.easyrenderer;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import exceptions.ParseException;
import model.Map;
import model.Node;
import parser.NodeParser;

public class NodeParserTest {

	@Test(expected = ParseException.class)
    public void testParserWithInvalidString() throws ParseException
    {
		NodeParser.parse("<foo");
    }
	
	@Test(expected = ParseException.class)
    public void testParserWithInvalidXml() throws ParseException
    {
		NodeParser.parse("<node id='2029712627' timestamp='2012-11-24T10:31:10Z' uid='219843' user='mides' version='1' changeset='14010209' lon='-1.120045' />");
	}
}
