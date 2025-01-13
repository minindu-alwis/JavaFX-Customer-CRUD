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

    private DBConnection() throws SQLException {

        this.connection = DriverManager.getConnection(URL, userName, password);
    }


    public Connection getConnection() {
        return connection;
    }

    public static DBConnection getInstance() throws SQLException {
        if (dbConnection == null) {
            dbConnection = new DBConnection();
        }
        return dbConnection;
    }
}
