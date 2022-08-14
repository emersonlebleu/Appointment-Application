package utilities;

import java.sql.Connection;
import java.sql.DriverManager;

/** Class to open and close the DB connection. Private variables for the URL, username and password are created along
 * with the driver and a new Connection variable conn. Class contains the "connect" and "disconnect" methods */
public abstract class JDBC {
    private static final String dbURL = "jdbc:mysql://localhost:3306/client_schedule?connectionTimeZone = SERVER";
    private static final String username = "sqlUser";
    private static final String password = "passw0rd!";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    public static Connection conn;

    /** Makes a connection using the dbURL, username, password and driver. If there is an error the error
     * is outputted to the console. */
    public static void connect() {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(dbURL, username, password);
            System.out.println("Connected");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    /** Disconnects. If there is an error the error is outputted to the console. */
    public static void disconnect() {
        try {
            conn.close();
            System.out.println("Closed");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
