package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.StageStyle;
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
import java.util.Iterator;
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
    public ComboBox<LocalTime> addStartTime;
    public ComboBox<LocalTime> addEndTime;
    public AnchorPane homePane;
    public TextField modIdField;
    public TextField modTitleField;
    public TextField modDescriptionField;
    public TextField modLocationField;
    public TextField modTypeField;
    public DatePicker modStartDateP;
    public DatePicker modEndDateP;
    public Button saveMod;
    public Button cancelMod;
    public ComboBox<Contact> modContactDropD;
    public ComboBox<LocalTime> modStartTime;
    public ComboBox<LocalTime> modEndTime;
    public ComboBox<Customer> modCustDropD;
    public ComboBox<User> modUserDropD;
    public RadioButton monthRadio;
    public RadioButton weekRadio;
    public RadioButton allRadio;
    public ToggleGroup viewToggle;

    private model.Appointment selectedAppointment;
    private static String startStyle;


    /** Function refreshes date in the appointments table */
    private void setApptTable(){
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        try {
            appointments = AppointmentDAO.getAllAppointments();
        } catch (Exception e) {
            System.out.println(e);
        }

        Iterator<Appointment> itr = appointments.iterator();

        if (weekRadio.isSelected()){
            for (Iterator<Appointment> it = itr; it.hasNext();) {
                Appointment appointment = it.next();
                switch (LocalDateTime.now().getDayOfWeek()){
                    case MONDAY:
                        if (appointment.getStart().isBefore(LocalDateTime.now().minusDays(1)) || appointment.getStart().isAfter(LocalDateTime.now().plusDays(5))){
                            itr.remove();
                        }
                        break;
                    case TUESDAY:
                        if (appointment.getStart().isBefore(LocalDateTime.now().minusDays(2)) || appointment.getStart().isAfter(LocalDateTime.now().plusDays(4))){
                            itr.remove();
                        }
                        break;
                    case WEDNESDAY:
                        if (appointment.getStart().isBefore(LocalDateTime.now().minusDays(3)) || appointment.getStart().isAfter(LocalDateTime.now().plusDays(3))){
                            itr.remove();
                        }
                        break;
                    case THURSDAY:
                        if (appointment.getStart().isBefore(LocalDateTime.now().minusDays(4)) || appointment.getStart().isAfter(LocalDateTime.now().plusDays(2))){
                            itr.remove();
                        }
                        break;
                    case FRIDAY:
                        if (appointment.getStart().isBefore(LocalDateTime.now().minusDays(5)) || appointment.getStart().isAfter(LocalDateTime.now().plusDays(1))){
                            itr.remove();
                        }
                        break;
                    case SATURDAY:
                        if (appointment.getStart().isBefore(LocalDateTime.now().minusDays(6)) || appointment.getStart().isAfter(LocalDateTime.now())) {
                            itr.remove();
                        }
                        break;
                    case SUNDAY:
                        if (appointment.getStart().isBefore(LocalDateTime.now()) || appointment.getStart().isAfter(LocalDateTime.now().plusDays(6))){
                            itr.remove();
                        }
                        break;
                }
            }
        } else if (monthRadio.isSelected()){
            for (Iterator<Appointment> it = itr; it.hasNext();){
                Appointment appointment = it.next();
                if (appointment.getStart().getMonth().equals(LocalDateTime.now().getMonth())) {
                    continue;
                } else {
                    itr.remove();
                }
            }
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
    private ObservableList<Customer> customers;
    private ObservableList<User> users;
    private ObservableList<Contact> contacts;

    /** Sets drop-downs on page */
    private void refreshDropdowns(){
        try {
            contacts = ContactDAO.getAllContacts();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            customers = CustomerDAO.getAllCustomers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            users = UserDAO.getAllUsers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //Set the choice box options add page
        addStartTime.setItems(times);
        addStartTime.setVisibleRowCount(8);
        addStartTime.setPromptText("Start Time...");

        addEndTime.setItems(times);
        addEndTime.setVisibleRowCount(8);
        addEndTime.setPromptText("End Time...");

        contactDropD.setItems(contacts);
        contactDropD.setVisibleRowCount(7);
        custDropD.setItems(customers);
        custDropD.setVisibleRowCount(7);
        userDropD.setItems(users);
        userDropD.setVisibleRowCount(7);

        //Set the choice box options mod page
        modStartTime.setItems(times);
        modStartTime.setVisibleRowCount(8);
        modStartTime.setPromptText("Start Time...");

        modEndTime.setItems(times);
        modEndTime.setVisibleRowCount(8);
        modEndTime.setPromptText("End Time...");

        modContactDropD.setItems(contacts);
        modContactDropD.setVisibleRowCount(7);
        modCustDropD.setItems(customers);
        modCustDropD.setVisibleRowCount(7);
        modUserDropD.setItems(users);
        modUserDropD.setVisibleRowCount(7);
    }

    public void clearAddFormFields(){
        titleField.clear();
        descriptionField.clear();
        locationField.clear();
        typeField.clear();
        startDateP.setValue(null);
        endDateP.setValue(null);
        addStartTime.getSelectionModel().clearSelection();
        addEndTime.getSelectionModel().clearSelection();
        custDropD.getSelectionModel().clearSelection();
        userDropD.getSelectionModel().clearSelection();
        contactDropD.getSelectionModel().clearSelection();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allRadio.setToggleGroup(viewToggle);
        monthRadio.setToggleGroup(viewToggle);
        weekRadio.setToggleGroup(viewToggle);
        allRadio.setSelected(true);

        refreshDropdowns();
        setApptTable();
        homePane.toFront();
    }

    public void add_appt(ActionEvent actionEvent) {
        addPane.toFront();
    }

    public void mod_appt(ActionEvent actionEvent) {
        selectedAppointment = (Appointment) apptTable.getSelectionModel().getSelectedItem();

        if (selectedAppointment == null) {
            //----------------No Selection Error--------------------//
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UTILITY);
            alert.setContentText("Please make a selection.");

            alert.showAndWait();
        } else {
            refreshDropdowns();
            modIdField.setText(String.valueOf(selectedAppointment.getId()));
            modTitleField.setText(selectedAppointment.getTitle());
            modDescriptionField.setText(selectedAppointment.getDescription());
            modLocationField.setText(selectedAppointment.getLocation());
            modTypeField.setText(selectedAppointment.getType());


            //Times Getting local date & time together
            LocalDate startDate = selectedAppointment.getStart().toLocalDate();
            LocalTime startTime = selectedAppointment.getStart().toLocalTime();
            LocalDate endDate = selectedAppointment.getEnd().toLocalDate();
            LocalTime endTime = selectedAppointment.getEnd().toLocalTime();

            modStartDateP.setValue(startDate);
            modStartTime.getSelectionModel().select(startTime);
            modEndDateP.setValue(endDate);
            modEndTime.getSelectionModel().select(endTime);

            Customer currCustomer = null;
            for (Customer customer: customers) {
                if (customer.getId() == selectedAppointment.getCustomer()){
                    currCustomer = customer;
                }
            }

            User currUser = null;
            for (User user: users) {
                if (user.getId() == selectedAppointment.getUser()){
                    currUser = user;
                }
            }

            Contact currContact = null;
            for (Contact contact: contacts) {
                if (contact.getId() == selectedAppointment.getContact()){
                    currContact = contact;
                }
            }

            modCustDropD.getSelectionModel().select(currCustomer);
            modUserDropD.getSelectionModel().select(currUser);
            modContactDropD.getSelectionModel().select(currContact);

            modPane.toFront();
        }


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
        clearAddFormFields();
        homePane.toFront();
    }

    public void onSaveAdd(ActionEvent actionEvent) {

        String title = titleField.getText();
        String description = descriptionField.getText();
        String location = locationField.getText();
        String type = typeField.getText();


        //Times Getting local date & time together
        LocalDate startDate = startDateP.getValue();
        LocalTime startTime = addStartTime.getValue();
        LocalDate endDate = endDateP.getValue();
        LocalTime endTime = addEndTime.getValue();

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

    public void onCancelMod(ActionEvent actionEvent) {
        homePane.toFront();
    }

    public void onSaveMod(ActionEvent actionEvent) {
        Integer id = Integer.parseInt(modIdField.getText());
        String title = modTitleField.getText();
        String description = modDescriptionField.getText();
        String location = modLocationField.getText();
        String type = modTypeField.getText();


        //Times Getting local date & time together
        LocalDate startDate = modStartDateP.getValue();
        LocalTime startTime = modStartTime.getValue();
        LocalDate endDate = modEndDateP.getValue();
        LocalTime endTime = modEndTime.getValue();

        LocalDateTime start = LocalDateTime.of(startDate, startTime);
        LocalDateTime end = LocalDateTime.of(endDate, endTime);

        Integer customerId = modCustDropD.getValue().getId();
        Integer userId = modUserDropD.getValue().getId();
        Integer contactId = modContactDropD.getValue().getId();

        model.Appointment newAppt = new Appointment(id, title, description, location, type, start, end, customerId, userId, contactId);
        try {
            AppointmentDAO.updateAppointment(newAppt);
            System.out.println("Updated!");
        } catch (Exception e) {
            System.out.println(e);
        }

        setApptTable();
        clearAddFormFields();
        homePane.toFront();
    }

    public void onMonth(ActionEvent actionEvent) {
        setApptTable();
    }

    public void onWeek(ActionEvent actionEvent) {
        setApptTable();
    }

    public void onAll(ActionEvent actionEvent) {
        setApptTable();
    }
}
