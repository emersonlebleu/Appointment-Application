package utilities;

import controller.Login;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

/** CRUD for customer interaction */
public abstract class CustomerDAO {

    /** Adds a new customer to the DB.
     * @param customer a customer to be added to the DB. */
    public static void addCustomer(model.Customer customer) throws SQLException {
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.conn.prepareStatement(sql);
        ps.setString(1, customer.getName());
        ps.setString(2, customer.getAddress());
        ps.setString(3, customer.getPostalCode());
        ps.setString(4, customer.getPhone());
        ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now().atZone(CurrentSession.getZone()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime()));
        String createdBy = "ScheduleAppUser: " + Login.currUser.getName();
        ps.setString(6, createdBy);
        ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now().atZone(CurrentSession.getZone()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime()));
        ps.setString(8, createdBy);
        ps.setInt(9, customer.getDivision());

        ps.executeUpdate();
    }

    /** gets a customer from the database by ID.
     * @param id a particular customer id.
     * @return a customer object with values from DB. */
    public static model.Customer getCustomer(Integer id) throws SQLException{
        String sql = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID FROM customers WHERE Customer_ID = ?";

        PreparedStatement ps = JDBC.conn.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()){
            model.Customer customer = new Customer(rs.getInt("Customer_ID"), rs.getString("Customer_Name"), rs.getString("Address"), rs.getString("Postal_Code"), rs.getString("Phone"), rs.getInt("Division_ID"));
            return customer;
        } else {
            return null;
        }
    }

    /** Update a customer given a customer object.
     * @param customer a customer object to set values to. */
    public static void updateCustomer(model.Customer customer) throws SQLException{
        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";

        PreparedStatement ps = JDBC.conn.prepareStatement(sql);
        ps.setString(1, customer.getName());
        ps.setString(2, customer.getAddress());
        ps.setString(3, customer.getPostalCode());
        ps.setString(4, customer.getPhone());
        ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now().atZone(CurrentSession.getZone()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime()));
        String createdBy = "ScheduleAppUser: " + Login.currUser.getName();
        ps.setString(6, createdBy);
        ps.setInt(7, customer.getDivision());
        ps.setInt(8, customer.getId());

        ps.executeUpdate();
    }

    /** Delete a given customer by id. */
    public static void deleteCustomer(Integer id) throws SQLException{
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";

        PreparedStatement ps = JDBC.conn.prepareStatement(sql);
        ps.setString(1, String.valueOf(id));
        ps.executeUpdate();
    }

    /** Get all customers in database and output to a list object.
     * @return a list of all customers */
    public static ObservableList<model.Customer> getAllCustomers() throws SQLException {
        ObservableList<model.Customer> customers = FXCollections.observableArrayList();
        String sql = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID FROM customers";

        PreparedStatement ps = JDBC.conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            customers.add(new Customer( rs.getInt("Customer_ID"), rs.getString("Customer_Name"), rs.getString("Address"), rs.getString("Postal_Code"), rs.getString("Phone"), rs.getInt("Division_ID")));
        }
        return customers;
    }
}
