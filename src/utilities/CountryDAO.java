package utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryDAO {
    /** Get all countries in database and output to a list object.
     * @return a list of all countries */
    public static ObservableList<Country> getAllCountries() throws SQLException {
        ObservableList<model.Country> countries = FXCollections.observableArrayList();
        String sql = "SELECT Country_ID, Country FROM countries";

        PreparedStatement ps = JDBC.conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            countries.add(new Country(rs.getInt("Country_ID"), rs.getString("Country")));
        }
        return countries;
    }
}
