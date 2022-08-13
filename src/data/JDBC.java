package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Class to open and close the DB connection. */
public abstract class JDBC {
    private static final String dbURL = "jdbc:mysql://localhost:3306/client_schedule?connectionTimeZone = SERVER";
    private static final String username = "sqlUser";
    private static final String password = "passw0rd!";
    private static final String driver = "com.mysql.cj.jdbc.Driver";

    public static Connection conn;

    public static void connect() {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(dbURL, username, password);
            System.out.println("Connected");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void disconnect() {
        try {
            conn.close();
            System.out.println("Closed");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
