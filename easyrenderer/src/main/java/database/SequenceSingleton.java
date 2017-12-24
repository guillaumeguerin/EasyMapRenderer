package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SequenceSingleton {

	private static volatile SequenceSingleton ss = null;
	
	private static volatile Double d = null;
	
	private SequenceSingleton() {}
	
	public static SequenceSingleton getInstance() throws SQLException {
		if(ss == null) {
			ss = new SequenceSingleton();
			d = new Double(0);
		}
		return ss;
	}
	
	public Double getId() {
		d += 1;
		return d;
	}
}
