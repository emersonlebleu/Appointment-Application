package utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/** CRUD for division interaction. */
public abstract class DivisionDAO {
    /** Get all divisions in database and output to a list object.
     * @return a list of all divisions */
    public static ObservableList<Division> getAllDivisions() throws SQLException {
        ObservableList<model.Division> divisions = FXCollections.observableArrayList();
        String sql = "SELECT Division_ID, Division, Country_ID FROM first_level_divisions";

        PreparedStatement ps = JDBC.conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            divisions.add(new Division(rs.getInt("Division_ID"), rs.getString("Division"), rs.getInt("Country_ID")));
        }
        return divisions;
    }
}
