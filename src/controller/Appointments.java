package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;
import utilities.AppointmentDAO;
import utilities.ContactDAO;
import utilities.CustomerDAO;
import utilities.UserDAO;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class Appointments implements Initializable {
    public TableView apptTable;
    public TableColumn idCol;
    public TableColumn titleCol;
    public TableColumn descCol;
    public TableColumn locationCol;
    public TableColumn typeCol;
    public TableColumn startCol;
    public TableColumn endCol;
    public TableColumn customerCol;
    public TableColumn userCol;
    public TableColumn contactCol;
    public Button addApptBtn;
    public Button modApptBtn;
    public Button delApptBtn;
    public ChoiceBox apptViewPicker;
    public AnchorPane fwdBackContainer;
    public Button timeBackBtn;
    public Button timeForwardBtn;
    public Label calNavUnitLabel;
    public AnchorPane modPane;
    public AnchorPane addPane;
    public StackPane apptStack;
    public Button cancelAdd;
    public Button saveAdd;
    public TextField idField;
    public TextField titleField;
    public TextField descriptionField;
    public TextField locationField;
    public TextField typeField;
    public ComboBox<Contact> contactDropD;
    public DatePicker startDateP;
    public DatePicker endDateP;
    public ComboBox<Customer> custDropD;
    public ComboBox<User> userDropD;
    public ComboBox<LocalTime> amPMStart;
    public ComboBox<LocalTime> amPMEnd;
    public AnchorPane homePane;


    private ObservableList<model.Appointment> appointments = FXCollections.observableArrayList();
    private model.Appointment selectedAppointment;
    private static String startStyle;

    public ObservableList<String> amPM = FXCollections.observableArrayList("AM", "PM");
    public ObservableList<String> moWK = FXCollections.observableArrayList("All", "Month", "Week");

    /** Function refreshes date in the appointments table */
    private void setApptTable(){
        try {
            appointments = AppointmentDAO.getAllAppointments();
        } catch (Exception e) {
            System.out.println(e);
        }

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startFormat"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endFormat"));
        customerCol.setCellValueFactory(new PropertyValueFactory<>("customer"));
        userCol.setCellValueFactory(new PropertyValueFactory<>("user"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));

        apptTable.setItems(appointments);
    }

    /** Function creates the list of times */
    private ObservableList<LocalTime> createTimes(){
        ObservableList<LocalTime> times = FXCollections.observableArrayList();
        LocalTime low = LocalTime.of(0, 0);
        LocalTime high = LocalTime.of(23, 45);

        while (low.isBefore(high) || low.equals(high)) {
            times.add(low);
            if (!low.equals(high)){
                low = low.plusMinutes(15);
            } else {
                break;
            }
        }
        return times;
    }
    /** Set the times to an object to be used */
    private ObservableList<LocalTime> times = createTimes();

    /** Sets drop-downs on page */
    private void refreshDropdowns(){
        ObservableList<model.Contact> contacts;
        try {
            contacts = ContactDAO.getAllContacts();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ObservableList<model.Customer> customers;
        try {
            customers = CustomerDAO.getAllCustomers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ObservableList<model.User> users;
        try {
            users = UserDAO.getAllUsers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //Set the choice box options
        amPMStart.setItems(times);
        amPMStart.setVisibleRowCount(8);
        amPMStart.setPromptText("Start Time...");

        amPMEnd.setItems(times);
        amPMEnd.setVisibleRowCount(8);
        amPMEnd.setPromptText("End Time...");

        apptViewPicker.getItems().addAll(moWK);
        apptViewPicker.setValue(moWK.get(0));

        contactDropD.setItems(contacts);
        contactDropD.setVisibleRowCount(7);
        custDropD.setItems(customers);
        custDropD.setVisibleRowCount(7);
        userDropD.setItems(users);
        userDropD.setVisibleRowCount(7);
    }

    public void clearAddFormFields(){
        titleField.clear();
        descriptionField.clear();
        locationField.clear();
        typeField.clear();
        startDateP.setValue(null);
        endDateP.setValue(null);
        amPMStart.getSelectionModel().clearSelection();
        amPMEnd.getSelectionModel().clearSelection();
        custDropD.getSelectionModel().clearSelection();
        userDropD.getSelectionModel().clearSelection();
        contactDropD.getSelectionModel().clearSelection();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setApptTable();
        refreshDropdowns();
    }

    public void add_appt(ActionEvent actionEvent) {
        addPane.toFront();
    }

    public void mod_appt(ActionEvent actionEvent) {
        modPane.toFront();
    }

    public void delete_appt(ActionEvent actionEvent) {
        selectedAppointment = (model.Appointment) apptTable.getSelectionModel().getSelectedItem();
        try {
            AppointmentDAO.deleteAppointment(selectedAppointment.getId());
            setApptTable();
        } catch (Exception e) {
            //FIX**
        }
    }

    public void mouseOvAdd(MouseEvent mouseEvent) {
        startStyle = addApptBtn.getStyle();
        addApptBtn.setStyle("-fx-background-color: #2F334B;");
    }

    public void mouseOutAdd(MouseEvent mouseEvent) {
        addApptBtn.setStyle(startStyle);
    }

    public void mouseOvMod(MouseEvent mouseEvent) {
        startStyle = modApptBtn.getStyle();
        modApptBtn.setStyle("-fx-background-color: #2F334B;");
    }

    public void mouseOutMod(MouseEvent mouseEvent) {
        modApptBtn.setStyle(startStyle);
    }

    public void mouseOvDel(MouseEvent mouseEvent) {
        startStyle = delApptBtn.getStyle();
        delApptBtn.setStyle("-fx-background-color: #2F334B;");
    }

    public void mouseOutDel(MouseEvent mouseEvent) {
        delApptBtn.setStyle(startStyle);
    }

    public void onCancelAdd(ActionEvent actionEvent) {
        homePane.toFront();
    }

    public void onSaveAdd(ActionEvent actionEvent) {

        String title = titleField.getText();
        String description = descriptionField.getText();
        String location = locationField.getText();
        String type = typeField.getText();


        //Times Getting local date & time together
        LocalDate startDate = startDateP.getValue();
        LocalTime startTime = amPMStart.getValue();
        LocalDate endDate = endDateP.getValue();
        LocalTime endTime = amPMEnd.getValue();

        LocalDateTime start = LocalDateTime.of(startDate, startTime);
        LocalDateTime end = LocalDateTime.of(endDate, endTime);

        Integer customerId = custDropD.getValue().getId();
        Integer userId = userDropD.getValue().getId();
        Integer contactId = contactDropD.getValue().getId();

        model.Appointment newAppt = new Appointment(title, description, location, type, start, end, customerId, userId, contactId);
        try {
            AppointmentDAO.addAppointment(newAppt);
            System.out.println("Added!");
        } catch (Exception e) {
            System.out.println(e);
        }

        setApptTable();
        clearAddFormFields();
        homePane.toFront();
    }
}
