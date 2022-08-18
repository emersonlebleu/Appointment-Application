package utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentDAO {

    public static void addAppointment(model.Appointment appointment){

    }
    public static model.Appointment getAppointment(Integer id){

        return null;
    }

    public static void updateAppointment(model.Appointment appointment){

    }

    public static String deleteAppointment(Integer id){

        return null;
    }

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
