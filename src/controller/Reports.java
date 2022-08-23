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
import java.util.Objects;
import java.util.ResourceBundle;

public class Reports implements Initializable {
    public Tab scheduleCurrUserTab;
    public Tab numApptTab;
    public Tab scheduleContactTab;
    public TableView totalBType;
    public TableView totalBMonth;
    public TableView totalBTyMo;
    public TableColumn tbtTypeCol;
    public TableColumn tbtApptCol;
    public TableColumn tbmMonthCol;
    public TableColumn tbmApptCol;
    public TextArea contactSchedulesRep;
    public TextArea userScheduleRep;
    public TableColumn tbtmMonthCol;
    public TableColumn tbtmTypeCol;
    public TableColumn tbtmApptCol;
    private ObservableList<Report> tbtmData = FXCollections.observableArrayList();
    private ObservableList<Report> tbtData = FXCollections.observableArrayList();
    private ObservableList<Report> tbmData = FXCollections.observableArrayList();

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

    private void genTableData(){
        try {
            tbtmData = ReportDAO.getNumApptByTypeMo();
            tbtData = ReportDAO.getNumApptByType();
            tbmData = ReportDAO.getNumApptByMo();
        } catch (Exception e) {

        }
    }
    public TableView getTotalBType(){
        return totalBType;
    }
    public TableView getTotalBMonth(){
        return totalBMonth;
    }
    public TableView getTotalBTyMo(){
        return totalBTyMo;
    }

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

    private void setContactReport() throws SQLException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        appointments = AppointmentDAO.getAllAppointments();
        Contact contact = null;
        String report = "";
        
        for (Appointment appointment: appointments){
            if (contact == null){
                contact = ContactDAO.getContact(appointment.getContact());
                report += contact.getName() + "\n";
                report += String.valueOf(appointment.getId()) + " | " + appointment.getTitle() + " | " + appointment.getType() + " | " + appointment.getDescription() + " | " + appointment.getStartFormat() + " | " + appointment.getEndFormat() + " | " + String.valueOf(appointment.getCustomer());
            } else if (contact.getId() == appointment.getContact()) {
                report += "\n";
                report += String.valueOf(appointment.getId()) + " | " + appointment.getTitle() + " | " + appointment.getType() + " | " + appointment.getDescription() + " | " + appointment.getStartFormat() + " | " + appointment.getEndFormat() + " | " + String.valueOf(appointment.getCustomer());
            } else if (contact.getId() != appointment.getContact()) {
                contact = ContactDAO.getContact(appointment.getContact());
                report += "\n\n";
                report += contact.getName() + "\n";
                report += String.valueOf(appointment.getId()) + " | " + appointment.getTitle() + " | " + appointment.getType() + " | " + appointment.getDescription() + " | " + appointment.getStartFormat() + " | " + appointment.getEndFormat() + " | " + String.valueOf(appointment.getCustomer());
            }
        }
        contactSchedulesRep.setText(report);
    }
    private void setUserReport() throws SQLException{
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        appointments = AppointmentDAO.getAllAppointments();
        User user = Login.getUser();
        String report = "Appointments for username: " + user.getName() + "\n";

        for (Appointment appointment: appointments){
            if (user.getId() == appointment.getUser()){
                report += "\n";
                report += String.valueOf(appointment.getId()) + " | " + appointment.getTitle() + " | " + appointment.getType() + " | " + appointment.getStartFormat() + " | " + appointment.getEndFormat() + " | " + CustomerDAO.getCustomer(appointment.getCustomer()) + " | " + ContactDAO.getContact(appointment.getContact());
            }
        }
        userScheduleRep.setText(report);
    }
}
