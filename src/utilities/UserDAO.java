package utilities;

import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Collection of login related query functions. Given what the assignment asks we do not need to have methods to
 * edit or add users only to get users. */
public abstract class UserDAO {

    /** Attempts to find a username password match for login.
     * @param username username from the input field on login page.
     * @param password password from the input field on login page.
     * @return true or false if it finds a match or doesn't. */
    public static boolean validateUser(String username, String password) throws SQLException {
        String sql = "SELECT User_Name, Password FROM users WHERE User_Name = ?";

        PreparedStatement ps = JDBC.conn.prepareStatement(sql);
        ps.setString(1, username);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            String pW = rs.getString("Password");
            if (pW.equals(password)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
    /** Attempts to find a username password match and if found gets the ID from the database.
     * @param username username.
     * @param password password.
     * @return an integer the ID if found otherwise null */
    public Integer getUserId(String username, String password) throws SQLException {
        Integer id = null;

        String sql = "SELECT User_ID FROM users WHERE User_Name = ? AND Password = ?";
        PreparedStatement ps = JDBC.conn.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();

        if (rs.next()){
            id = rs.getInt("User_ID");
        }

        return id;
    }
    /** Attempts to find a user in the db by ID.
     * @param id the id of the record to find.
     * @return a user object */
    public model.User getUser(Integer id) throws SQLException{
        String sql = "SELECT User_ID, User_Name, Password FROM users WHERE User_ID = ?";
        model.User user = null;

        PreparedStatement ps = JDBC.conn.prepareStatement(sql);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            user = new User(rs.getInt("User_ID"), rs.getString("User_Name"), rs.getString("Password"));
        }
        return user;
    }
}
