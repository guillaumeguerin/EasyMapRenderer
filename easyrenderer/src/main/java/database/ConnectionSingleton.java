package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSingleton {

	private static volatile ConnectionSingleton cs = null;
	
	private static volatile Connection c = null;
	
	private ConnectionSingleton() {}
	
	public static ConnectionSingleton getInstance() throws SQLException {
		if(cs == null) {
			cs = new ConnectionSingleton();
			c = DriverManager.getConnection("jdbc:sqlite:test.db");
		}
		return cs;
	}
	
	public Connection getConnection() {
		return c;
	}
}
