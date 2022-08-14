package utilities;

import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class LoginQuery {

    /**  */
    public static boolean findUser(String username, String password) throws SQLException {
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
}
