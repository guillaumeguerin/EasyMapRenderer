package database;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Node;
import model.Map;
import model.Way;

public class SQLiteJDBC {
  public static void initTables() {
      Connection c = null;
      
      try {
         Class.forName("org.sqlite.JDBC");
         c = DriverManager.getConnection("jdbc:sqlite:test.db");
         
         System.out.println("Opened database successfully");
         Statement stmt = null;
         stmt = c.createStatement();
         String sql = "CREATE TABLE NODE " +
                        "(ID INT PRIMARY KEY     NOT NULL," +
                        " LAT            REAL    NOT NULL, " + 
                        " LON            REAL    NOT NULL, " +
                        " WAY            INT     NOT NULL)";
         stmt.executeUpdate(sql);
         
         sql = "CREATE TABLE WAY " +
                 "(ID INT PRIMARY KEY     NOT NULL," +
                 " TYPE1          TEXT, " + 
                 " TYPE2          TEXT)";
         stmt.executeUpdate(sql);
         
         stmt.close();
         c.close();
      } catch ( Exception e ) {
         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
         System.exit(0);
      }
      System.out.println("Opened database successfully");
   }
  
  public static boolean tableExists(String tableName)
  {
	  String sql = "SELECT COUNT(*) AS NB_TABLE FROM sqlite_master WHERE type = \"table\" AND name = \"" + tableName + "\"";
      
      try {
    	   Class.forName("org.sqlite.JDBC");
    	   Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
           Statement stmt  = conn.createStatement();
           ResultSet rs    = stmt.executeQuery(sql);
          
          // loop through the result set
          while (rs.next()) {
              return (rs.getInt("NB_TABLE")) > 0;
          }
          
          stmt.close();
          conn.close();
      } 
      catch (Exception e) {
          System.out.println(e.getMessage());
      }
      return false;
  }

	public static void insertNode(Node node) {
		String sql = "INSERT INTO NODE(ID,LAT,LON,WAY) VALUES(?,?,?,?)";
		 
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, node.getId().intValue());
            pstmt.setDouble(2, node.getLat());
            pstmt.setDouble(3, node.getLon());
            pstmt.setInt(4, 0);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
	}
	
	public static void insertWay(Way way) {
		String sql = "INSERT INTO WAY(ID,TYPE1,TYPE2) VALUES(?,?,?)";
		 
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, way.getId().intValue());
            pstmt.setString(2, way.getType1());
            pstmt.setString(3, way.getType2());
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
	}
	
	public static void updateNode(Way w, int nodeID) {
		String sql = "UPDATE NODE SET WAY = ? WHERE ID = ?";
		 
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, w.getId().intValue());
            pstmt.setInt(2, nodeID);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
	}
	
	public static void updateAllNodes(Way w) {
		for(int i=0; i<w.getNodes().size(); i++) {
			 updateNode(w, w.getNodes().get(i).intValue());
		}
	}
	
	public static void dropDatabase() {
		//
	}
	
	public static Map retrieveMapFromDB(Double minLat, Double maxLat, Double minLon, Double maxLon, int zoomLevel)
	  {
		List<Node> resultNodes = new ArrayList<Node>();
		List<Way> resultWays = new ArrayList<Way>();
		Map m = new Map();
		
		  String sql = "SELECT NODE.ID, NODE.LAT, NODE.LON, WAY.TYPE1, WAY.TYPE2, NODE.WAY FROM NODE INNER JOIN WAY ON NODE.WAY = WAY.ID WHERE LAT >= "+ minLat +" AND LAT <= " + maxLat + " AND LON >= "+ minLon +" AND LON <= " + maxLon + " ";
	      sql += "AND TYPE1 IN (\"natural\"";
	      if(zoomLevel > 1) {
	    	  sql += ", \"\"";
	      }
	      if(zoomLevel > 2) {
	    	  sql += ", \"landuse\"";
	      }
	      if(zoomLevel > 3) {
	    	  sql += ", \"\"";
	      }
	      if(zoomLevel > 4) {
	    	  sql += ", \"parking\"";
	      }
	      if(zoomLevel > 5) {
	    	  sql += ", \"highway\"";
	      }
	      if(zoomLevel > 6) {
	    	  sql += ", \"surface\"";
	      }
	      if(zoomLevel > 7) {
	    	  sql += ", \"junction\"";
	      }
	      if(zoomLevel > 8) {
	    	  sql += ", \"railway\"";
	      }
	      if(zoomLevel > 9) {
	    	  sql += ", \"sport\"";
	      }
	      if(zoomLevel > 10) {
	    	  sql += ", \"leisure\"";
	      }
	      if(zoomLevel > 11) {
	    	  sql += ", \"tunnel\"";
	      }
	      if(zoomLevel > 12) {
	    	  sql += ", \"\"";
	      }
	      if(zoomLevel > 13) {
	    	  sql += ", \"\"";
	      }
	      if(zoomLevel > 14) {
	    	  sql += ", \"\"";
	      }
	      if(zoomLevel > 15) {
	    	  sql += ", \"\"";
	      }
	      if(zoomLevel > 16) {
	    	  sql += ", \"\"";
	      }
	      if(zoomLevel > 17) {
	    	  sql += ", \"\"";
	      }
	      if(zoomLevel > 18) {
	    	  sql += ", \"\"";
	      }
	      sql += ") AND TYPE2 IN (\"coastline\"";
	      if(zoomLevel > 1) {
	    	  sql += ", \"water\", \"sand\"";
	      }
	      if(zoomLevel > 2) {
	    	  sql += ", \"forest\"";
	      }
	      if(zoomLevel > 3) {
	    	  sql += ", \"wetland\"";
	      }
	      if(zoomLevel > 4) {
	    	  sql += ", \"surface\"";
	      }
	      if(zoomLevel > 5) {
	    	  sql += ", \"primary\"";
	      }
	      if(zoomLevel > 6) {
	    	  sql += ", \"roundabout\", \"rail\"";
	      }
	      if(zoomLevel > 7) {
	    	  sql += ", \"residential\"";
	      }
	      if(zoomLevel > 8) {
	    	  sql += ", \"service\"";
	      }
	      if(zoomLevel > 9) {
	    	  sql += ", \"oneway\"";
	      }
	      if(zoomLevel > 10) {
	    	  sql += ", \"cycleway\"";
	      }
	      if(zoomLevel > 11) {
	    	  sql += ", \"tertiary\"";
	      }
	      if(zoomLevel > 12) {
	    	  sql += ", \"path\"";
	      }
	      if(zoomLevel > 13) {
	    	  sql += ", \"\"";
	      }
	      if(zoomLevel > 14) {
	    	  sql += ", \"gymnastics\"";
	      }
	      if(zoomLevel > 15) {
	    	  sql += ", \"miniature_golf\"";
	      }
	      if(zoomLevel > 16) {
	    	  sql += ", \"\"";
	      }
	      if(zoomLevel > 17) {
	    	  sql += ", \"\"";
	      }
	      if(zoomLevel > 18) {
	    	  sql += ", \"\"";
	      }
	      sql += ") ORDER BY WAY.ID";
	      sql = sql.replace("\"\",", "");
	      sql = sql.replace(", \"\"", "");
	      try {
	    	   Class.forName("org.sqlite.JDBC");
	    	   Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
	           Statement stmt  = conn.createStatement();
	           ResultSet rs    = stmt.executeQuery(sql);
	          
	           int previousWayId = -1;
	           List<Double> currentWayNodeIds = new ArrayList<Double>();
	          // loop through the result set
	          while (rs.next()) {
	        	  int nodeId = rs.getInt("ID");
	        	  Node n = new Node(new Double(nodeId), rs.getDouble("LAT"), rs.getDouble("LON"));
	              resultNodes.add(n);
	              currentWayNodeIds.add(new Double(nodeId));
	              int currentWayId = rs.getInt("WAY");
	              if(previousWayId == -1 || previousWayId != currentWayId) {
	            	  Way w = new Way(new Double(previousWayId), rs.getString("TYPE1"), rs.getString("TYPE2"));
	            	  w.setNodes(currentWayNodeIds);
	            	  resultWays.add(w);
	            	  currentWayNodeIds = new ArrayList<Double>();
	            	  previousWayId = currentWayId;
	              }
	              
	          }
	          
	          stmt.close();
	          conn.close();
	          
	          m.setNodes(resultNodes);
	          m.setWays(resultWays);
	      } 
	      catch (Exception e) {
	          System.out.println(e.getMessage());
	      }
	      
	      return m;
	  }
	
	/*public static List<Node> getWaysById(List<Integer> ids)
	  {
		List<Node> results = new ArrayList<Node>();
		  String sql = "SELECT WAY.ID, WAY.TYPE1, WAY.TYPE2 FROM WAY WHERE WAY.ID IN ("+ + ")";

	  }*/
}