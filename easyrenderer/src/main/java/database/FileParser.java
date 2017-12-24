package database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exceptions.DrawingException;
import exceptions.ParseException;
import model.Map;
import model.Node;
import model.Relation;
import model.Tag;
import model.Way;
import parser.XMLParserFacade;

public class FileParser {

	
	
    public static void insertData(List<Object> osmList) {
    	for(int i=0; i< osmList.size(); i++) {
			if(osmList.get(i) instanceof Node) {
				SQLiteJDBC.insertNode((Node) osmList.get(i));
			}
			else if(osmList.get(i) instanceof Way) {
				SQLiteJDBC.insertWay((Way) osmList.get(i));
			}
			else if(osmList.get(i) instanceof Relation) {
				SQLiteJDBC.insertRelation((Relation) osmList.get(i));
			}
			else if(osmList.get(i) instanceof Tag) {
				SQLiteJDBC.insertTag((Tag) osmList.get(i));
			}
		}
		
		for(int i=0; i< osmList.size(); i++) {
			if(osmList.get(i) instanceof Way) {
				SQLiteJDBC.updateAllNodes((Way) osmList.get(i));
			}
		}
    }
    
    public static void readFile(String filePath) {
    	BufferedReader br = null;
		FileReader fr = null;

		try {

			//br = new BufferedReader(new FileReader(FILENAME));
			fr = new FileReader(filePath);
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
				if(!o.equals(new Object())) {
					osmList.add(o);
				}
			}
			if(!SQLiteJDBC.tableExists("NODE") && !SQLiteJDBC.tableExists("WAY")
					&& !SQLiteJDBC.tableExists("RELATION")
					&& !SQLiteJDBC.tableExists("WAY_TAG")
					&& !SQLiteJDBC.tableExists("RELATION_TAG")) {
				try {
					SQLiteJDBC.initTables();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			insertData(osmList);
			
			//Map myMap = SQLiteJDBC.retrieveMapFromDB(new Double(-85), new Double(85), new Double(-180), new Double(180), 15);
			
			//drawMap(myMap);
			
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		finally {
			try {
				if (br != null) {
					br.close();
				}
				if (fr != null) {
					fr.close();
				}
			} 
			catch (IOException ex) {
				ex.printStackTrace();
			}

		}
	}
}
