package utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** CRUD for contact interaction */
public class ContactDAO {
    /** Adds a new contact to the DB.
     * @param contact a contact to be added to the DB. */
    public static void addContact(model.Contact contact) throws SQLException {
        String sql = "INSERT INTO contacts (Contact_Name, Email) VALUES (?, ?)";
        PreparedStatement ps = JDBC.conn.prepareStatement(sql);
        ps.setString(1, contact.getName());
        ps.setString(2, contact.getEmail());

        ps.executeQuery();
    }

    /** gets a contact from the database by ID.
     * @param id a particular contact id.
     * @return a contact object with values from DB. */
    public static model.Contact getContact(Integer id) throws SQLException{
        String sql = "SELECT Contact_ID, Contact_Name, Email FROM contacts WHERE Contact_ID = ?";

        PreparedStatement ps = JDBC.conn.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()){
            model.Contact contact = new Contact(rs.getInt("Contact_ID"), rs.getString("Contact_Name"), rs.getString("Email"));
            return contact;
        } else {
            return null;
        }
    }

    /** Update a contact given a contact object.
     * @param contact a contact object to set values to. */
    public static void updateContact(model.Contact contact) throws SQLException{
        String sql = "UPDATE contacts SET Contact_Name = ?, Email = ? WHERE Contact_ID = ?";

        PreparedStatement ps = JDBC.conn.prepareStatement(sql);
        ps.setString(1, contact.getName());
        ps.setString(2, contact.getEmail());
        ps.setInt(3, contact.getId());

        ResultSet rs = ps.executeQuery();
    }

    /** Delete a given contact by id. */
    public static void deleteContact(Integer id) throws SQLException{
        String sql = "DELETE FROM contacts WHERE Contact_ID = ?";

        PreparedStatement ps = JDBC.conn.prepareStatement(sql);
        ps.setString(1, String.valueOf(id));
        ResultSet rs = ps.executeQuery();
    }

    /** Get all contacts in database and output to a list object.
     * @return a list of all contacts */
    public static ObservableList<model.Contact> getAllContacts() throws SQLException {
        ObservableList<model.Contact> contacts = FXCollections.observableArrayList();
        String sql = "SELECT Contact_ID, Contact_Name, Email FROM contacts";

        PreparedStatement ps = JDBC.conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            contacts.add(new Contact( rs.getInt("Contact_ID"), rs.getString("Contact_Name"), rs.getString("Email")));
        }
        return contacts;
    }
}
