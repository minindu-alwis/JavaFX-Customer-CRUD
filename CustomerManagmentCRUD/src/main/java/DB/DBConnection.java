package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private Connection connection;
    private static DBConnection dbConnection;
    private final String URL = "jdbc:mysql://localhost/thogakade";
    private final String userName = "root";
    private final String password = "1234";

    // Private constructor to enforce singleton pattern
    private DBConnection() throws SQLException {
        // Assign the connection object
        this.connection = DriverManager.getConnection(URL, userName, password);
    }

    // Method to get the Connection object
    public Connection getConnection() {
        return connection;
    }

    // Method to get a singleton instance of DBConnection
    public static DBConnection getInstance() throws SQLException {
        if (dbConnection == null) {
            dbConnection = new DBConnection();
        }
        return dbConnection;
    }
}
