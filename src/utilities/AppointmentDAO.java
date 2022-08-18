package utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentDAO {

    public static void addAppointment(model.Appointment appointment) throws SQLException{
        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.conn.prepareStatement(sql);
        ps.setString(1, appointment.getTitle());
        ps.setString(2, appointment.getDescription());
        ps.setString(3, appointment.getLocation());
        ps.setString(4, appointment.getType());
        ps.setTimestamp(5, appointment.getStart());
        ps.setTimestamp(6, appointment.getEnd());
        ps.setInt(7, appointment.getCustomer());
        ps.setInt(8, appointment.getUser());
        ps.setInt(9, appointment.getContact());

        ps.executeQuery();

    }
    public static model.Appointment getAppointment(Integer id){

        return null;
    }

    public static void updateAppointment(model.Appointment appointment){

    }

    /** Delete a given appointment by id. */
    public static void deleteAppointment(Integer id) throws SQLException{
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";

        PreparedStatement ps = JDBC.conn.prepareStatement(sql);
        ps.setString(1, String.valueOf(id));
        ResultSet rs = ps.executeQuery();
    }

    /** Get all appointments in database and output to a list object.
     * @return a list of all appointments */
    public static ObservableList<model.Appointment> getAllAppointments() throws SQLException {
        ObservableList<model.Appointment> appointments = FXCollections.observableArrayList();
        String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID FROM appointments";

        PreparedStatement ps = JDBC.conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            appointments.add(new Appointment( rs.getInt("Appointment_ID"), rs.getString("Title"), rs.getString("Description"), rs.getString("Location"), rs.getString("Type"), rs.getTimestamp("Start"), rs.getTimestamp("End"), rs.getInt("Customer_ID"), rs.getInt("User_ID"), rs.getInt("Contact_ID")));
        }

        return appointments;
    }
}
