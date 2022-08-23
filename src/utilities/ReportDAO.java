package utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Report;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ReportDAO {
    /** Get num of appointment by each month and type input each record into a list object.
     * @return a list of report records */
    public static ObservableList<Report> getNumApptByTypeMo() throws SQLException {
        ObservableList<Report> reportRecords = FXCollections.observableArrayList();
        String sql = "SELECT COUNT(Appointment_ID) AS numAppt, Type, MONTH(Start) AS month FROM client_schedule.appointments group by month, Type";

        PreparedStatement ps = JDBC.conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            reportRecords.add(new Report(rs.getInt("month"), rs.getString("Type"), rs.getInt("numAppt")));
        }

        return reportRecords;
    }

    /** Get num of appointment by each month input each record into a list object.
     * @return a list of report records */
    public static ObservableList<Report> getNumApptByMo() throws SQLException {
        ObservableList<Report> reportRecords = FXCollections.observableArrayList();
        String sql = "SELECT COUNT(Appointment_ID) AS numAppt, MONTH(Start) AS month FROM client_schedule.appointments group by month";

        PreparedStatement ps = JDBC.conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            reportRecords.add(new Report(rs.getInt("month"), rs.getInt("numAppt")));
        }

        return reportRecords;
    }

    /** Get num of appointment by each type input each record into a list object.
     * @return a list of report records */
    public static ObservableList<Report> getNumApptByType() throws SQLException {
        ObservableList<Report> reportRecords = FXCollections.observableArrayList();
        String sql = "SELECT COUNT(Appointment_ID) AS numAppt, Type FROM client_schedule.appointments group by Type";

        PreparedStatement ps = JDBC.conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            reportRecords.add(new Report(rs.getString("Type"), rs.getInt("numAppt")));
        }

        return reportRecords;
    }
}
