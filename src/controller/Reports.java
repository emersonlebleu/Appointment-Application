package controller;

import com.mysql.cj.log.Log;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;
import model.Contact;
import model.Report;
import model.User;
import utilities.AppointmentDAO;
import utilities.ContactDAO;
import utilities.CustomerDAO;
import utilities.ReportDAO;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/** Reports Controller. */
public class Reports implements Initializable {
    /** Tab for current user schedule report. */
    public Tab scheduleCurrUserTab;
    /** Tab for number of appointments report. */
    public Tab numApptTab;
    /** Tab for all contacts' schedules report. */
    public Tab scheduleContactTab;
    /** Table for total by type */
    public TableView totalBType;
    /** Table for total by month */
    public TableView totalBMonth;
    /** Table for total by type and month */
    public TableView totalBTyMo;
    /** Total by type type column. */
    public TableColumn tbtTypeCol;
    /** Total by type appointment column. */
    public TableColumn tbtApptCol;
    /** Total by month, month column. */
    public TableColumn tbmMonthCol;
    /** Total by month appointment column. */
    public TableColumn tbmApptCol;
    /** Text area for contacts schedule report */
    public TextArea contactSchedulesRep;
    /** Text area for current user schedule report */
    public TextArea userScheduleRep;
    /** Total by type and month, month column. */
    public TableColumn tbtmMonthCol;
    /** Total by type and month type column. */
    public TableColumn tbtmTypeCol;
    /** Total by type and month appointment column. */
    public TableColumn tbtmApptCol;
    /** A list to hold the total by month and type data. */
    private ObservableList<Report> tbtmData = FXCollections.observableArrayList();
    /** A list to hold the total by type data. */
    private ObservableList<Report> tbtData = FXCollections.observableArrayList();
    /** A list to hold the total by month data. */
    private ObservableList<Report> tbmData = FXCollections.observableArrayList();
    /** The initialize function for this Reports controller. Calls the functions to populate the tables and generate the reports. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        genTableData();
        setTables();
        try {
            setContactReport();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            setUserReport();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /** Creates the table data for the three tables. */
    private void genTableData(){
        try {
            tbtmData = ReportDAO.getNumApptByTypeMo();
            tbtData = ReportDAO.getNumApptByType();
            tbmData = ReportDAO.getNumApptByMo();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /** Populates the tables with the appropriate data. */
    private void setTables(){
        tbtTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        tbtApptCol.setCellValueFactory(new PropertyValueFactory<>("numberAppt"));
        totalBType.setItems(tbtData);

        tbmMonthCol.setCellValueFactory(new PropertyValueFactory<>("month"));
        tbmApptCol.setCellValueFactory(new PropertyValueFactory<>("numberAppt"));
        totalBMonth.setItems(tbmData);

        tbtmApptCol.setCellValueFactory(new PropertyValueFactory<>("numberAppt"));
        tbtmMonthCol.setCellValueFactory(new PropertyValueFactory<>("month"));
        tbtmTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        totalBTyMo.setItems(tbtmData);
    }
    /** Creates a text based report for viewing appointment scheduled for each contact. */
    private void setContactReport() throws SQLException {
        ObservableList<Appointment> appointments;
        appointments = AppointmentDAO.getAllAppointments();
        Contact contact = null;
        String report = "";

        if (appointments != null){
            for (Appointment appointment: appointments){
                if (contact == null){
                    contact = ContactDAO.getContact(appointment.getContact());
                    report += contact.getName() + "\n";
                    report += appointment.getId() + " | " + appointment.getTitle() + " | " + appointment.getType() + " | " + appointment.getDescription() + " | " + appointment.getStartFormat() + " | " + appointment.getEndFormat() + " | " + String.valueOf(appointment.getCustomer());
                } else if (contact.getId() == appointment.getContact()) {
                    report += "\n";
                    report += appointment.getId() + " | " + appointment.getTitle() + " | " + appointment.getType() + " | " + appointment.getDescription() + " | " + appointment.getStartFormat() + " | " + appointment.getEndFormat() + " | " + String.valueOf(appointment.getCustomer());
                } else if (contact.getId() != appointment.getContact()) {
                    contact = ContactDAO.getContact(appointment.getContact());
                    report += "\n\n";
                    report += contact.getName() + "\n";
                    report += appointment.getId() + " | " + appointment.getTitle() + " | " + appointment.getType() + " | " + appointment.getDescription() + " | " + appointment.getStartFormat() + " | " + appointment.getEndFormat() + " | " + String.valueOf(appointment.getCustomer());
                }
            }
            contactSchedulesRep.setText(report);
        } else {
            contactSchedulesRep.setText("No appointments found.");
        }
    }
    /** Creates a text based report for viewing appointments for the sessions current logged-in user. */
    private void setUserReport() throws SQLException{
        ObservableList<Appointment> appointments;
        appointments = AppointmentDAO.getAllAppointments();
        User user = Login.getUser();
        String report = "Appointments for username: " + user.getName() + "\n";
        boolean info = false;

        if (appointments != null){
            for (Appointment appointment: appointments){
                if (user.getId() == appointment.getUser()){
                    report += "\n";
                    report += appointment.getId() + " | " + appointment.getTitle() + " | " + appointment.getType() + " | " + appointment.getStartFormat() + " | " + appointment.getEndFormat() + " | " + CustomerDAO.getCustomer(appointment.getCustomer()) + " | " + ContactDAO.getContact(appointment.getContact());
                    info = true;
                }
            }
            userScheduleRep.setText(report);
        } else {
            userScheduleRep.setText("No appointments found for: " + user.getName());
        }
    }
}
