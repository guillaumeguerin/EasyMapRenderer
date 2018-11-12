package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton class used to manage connection to the SQLLite Database.
 */
public class ConnectionSingleton {

    private static volatile ConnectionSingleton cs = null;

    private static volatile Connection c = null;

    /**
     * Default constructor.
     */
    private ConnectionSingleton() {
        //Empty on purpose.
    }

    /**
     * Retrieve the instance of the singleton
     *
     * @return the connection singleton
     * @throws SQLException when a problem with the sqlite db occured
     */
    public static ConnectionSingleton getInstance() throws SQLException {
        if (cs == null) {
            cs = new ConnectionSingleton();
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
        }
        return cs;
    }

    /**
     * Retrieve the connection to the database
     *
     * @return the connection
     */
    public Connection getConnection() {
        return c;
    }
}
