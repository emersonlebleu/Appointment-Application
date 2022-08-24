package controller;

import interfaces.Check;
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
import utilities.*;

import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;

/** Appointments controller. Contains all methods for the appointments interface. */
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

    /** A variable to store the appointment selected in appointments table. */
    private model.Appointment selectedAppointment;
    /** Used to store the initial state of style for an element. Used for the button animations. */
    private static String startStyle;
    /** Put the times into an object to be used */
    private final ObservableList<LocalTime> times = createTimes();
    /** To be filled, list of customers for drop-downs. */
    private ObservableList<Customer> customers;
    /** To be filled, list of users for drop-downs. */
    private ObservableList<User> users;
    /** To be filled, list of contacts for drop-downs. */
    private ObservableList<Contact> contacts;
    /** The initialize function for this controller. */
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
    /** On action of the add appointment button the add Pane is brought to the front of the view. */
    public void add_appt(ActionEvent actionEvent) {
        addPane.toFront();
    }

    /** LAMBDA USED HERE*** This lambda passes the required parameters to the functional "check"
     * interface method. This creates a "popup" alert of the type passed in with the title and message.
     * This allows for the creation of a popup within the controller logic and
     * shortens the within controller logic down considerably. As a note I would not have typically put this inside of one particular interface method but defined
     * outside and then used in multiple different instances however to meet requirements the lambda needed to be inside a
     * particular method so that I could comment and point it out. If used multiple times however this would have made
     * the creating of the "Check" worth it in terms of code brevity as each subsequent alert could have been just the one line
     * "check.popUp(**insert arguments**"" etc. Additionally this mod appointment function brings the mod appointment pane to front
     * and pre-fills the fields with data from the selected appointment from the appointments table. */
    public void mod_appt(ActionEvent actionEvent) {
        //Lambda Use
        Check check = (Check.Type type, String title, String message) -> {
            if (type == Check.Type.ERROR){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(title);
                alert.setHeaderText(null);
                alert.initStyle(StageStyle.UTILITY);
                alert.setContentText(message);

                alert.showAndWait();
            } else if (type == Check.Type.CONFIRMATION) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle(title);
                alert.setHeaderText(null);
                alert.setContentText(message);
                ButtonType buttonTypeYes = new ButtonType("Yes");
                ButtonType buttonTypeNo = new ButtonType("No");

                alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
                Optional<ButtonType> result = alert.showAndWait();
            }
        };
        selectedAppointment = (Appointment) apptTable.getSelectionModel().getSelectedItem();

        if (selectedAppointment == null) {
            //USE OF CHECK******
            check.popUp(Check.Type.ERROR, "No Selection", "Please make a selection");
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
    /** Deletes from the database the appointment that corresponds to the selected appointment from the table. Confirms this action and
     * if there is no selection gives an error message indicating as such. Provides a popup of deleted appointment information on success. */
    public void delete_appt(ActionEvent actionEvent) {
        selectedAppointment = (model.Appointment) apptTable.getSelectionModel().getSelectedItem();

        if (selectedAppointment == null) {
            //----------------No Selection Error--------------------//
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UTILITY);
            alert.setContentText("Please make a selection.");

            alert.showAndWait();
        } else {
            //--------------Remove Confirmation Box----------------------//
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Cancel Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you'd like to cancel this appointment?");
            ButtonType buttonTypeYes = new ButtonType("Yes");
            ButtonType buttonTypeNo = new ButtonType("No");

            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeYes) {
                try {
                    AppointmentDAO.deleteAppointment(selectedAppointment.getId());
                    setApptTable();
                } catch (Exception e) {
                    //FIX**
                }
            } else {
                //Do nothing
            }
            //----------------Canceled Appointment--------------------//
            Alert notification = new Alert(Alert.AlertType.INFORMATION);
            notification.setTitle("Canceled Appointment");
            notification.setHeaderText(null);
            notification.initStyle(StageStyle.UTILITY);
            notification.setContentText("Canceled Appointment Number: " + selectedAppointment.getId() + "\n" + "Type: " + selectedAppointment.getType());

            notification.showAndWait();
        }
    }
    /** Functionality for styling button on mouse enter/exit. */
    public void mouseOvAdd(MouseEvent mouseEvent) {
        startStyle = addApptBtn.getStyle();
        addApptBtn.setStyle("-fx-background-color: #2F334B;");
    }
    /** Functionality for styling button on mouse enter/exit. */
    public void mouseOutAdd(MouseEvent mouseEvent) {
        addApptBtn.setStyle(startStyle);
    }
    /** Functionality for styling button on mouse enter/exit. */
    public void mouseOvMod(MouseEvent mouseEvent) {
        startStyle = modApptBtn.getStyle();
        modApptBtn.setStyle("-fx-background-color: #2F334B;");
    }
    /** Functionality for styling button on mouse enter/exit. */
    public void mouseOutMod(MouseEvent mouseEvent) {
        modApptBtn.setStyle(startStyle);
    }
    /** Functionality for styling button on mouse enter/exit. */
    public void mouseOvDel(MouseEvent mouseEvent) {
        startStyle = delApptBtn.getStyle();
        delApptBtn.setStyle("-fx-background-color: #2F334B;");
    }
    /** Functionality for styling button on mouse enter/exit. */
    public void mouseOutDel(MouseEvent mouseEvent) {
        delApptBtn.setStyle(startStyle);
    }

    /** Resets the form fields to blank/null and moves home pane to front. */
    public void onCancelAdd(ActionEvent actionEvent) {
        clearAddFormFields();
        homePane.toFront();
    }
    /** Calls the validation function (this is passed to an alert as long as there is something to alert about remaining)
     * and once this passes inputs information from fields into a new appointment object
     * and passes the object to the appointmentsDAO.addAppointment() function to be added to database. Finally, resets the appointment
     * table, clears the form fields and moves the home pane to front. */
    public void onSaveAdd(ActionEvent actionEvent) throws SQLException {
        if (validateAdd() == null) {
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
            } catch (Exception e) {
                System.out.println(e);
            }

            setApptTable();
            clearAddFormFields();
            homePane.toFront();
        } else {
            //----------------Non valid Error--------------------//
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Form Submit Error");
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UTILITY);
            alert.setContentText(validateAdd());

            alert.showAndWait();
        }

    }
    /** Moves home pane to front if modification is canceled. */
    public void onCancelMod(ActionEvent actionEvent) {
        homePane.toFront();
    }
    /** Calls the validation function (this is passed to an alert as long as there is something to alert about remaining)
     *  and once this passes, inputs information from fields into a new appointment object
     * and passes the object to the appointmentsDAO.updateAppointment() function to be added to database. Finally, resets the appointment
     * table and moves the home pane to front. */
    public void onSaveMod(ActionEvent actionEvent) throws SQLException {
        if (validateMod() == null) {
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
            homePane.toFront();
        } else {
            //----------------Non valid Error--------------------//
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Form Submit Error");
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UTILITY);
            alert.setContentText(validateMod());

            alert.showAndWait();
        }

    }
    /** Month radio button select refreshes the appointments table with the proper filter indication in this case this month's appointments*/
    public void onMonth(ActionEvent actionEvent) {
        setApptTable();
    }
    /** Week radio button select refreshes the appointments table with the proper filter indication in this case this week (Sunday to Saturday) appointments*/
    public void onWeek(ActionEvent actionEvent) {
        setApptTable();
    }
    /** All radio button select refreshes the appointments table with the proper filter indication in this case all appointments. */
    public void onAll(ActionEvent actionEvent) {
        setApptTable();
    }

    //-------------------------------------------------------------------FUNCTION DECLARATIONS--------------------------------------------------
    /** Function refreshes the appointments table. Will refresh the data in the table with all appointments, this month's appointments, or this week's
     * appointments depending on the current state of the toggle group of radio buttons. */
    private void setApptTable(){
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        try {
            appointments = AppointmentDAO.getAllAppointments();
        } catch (Exception e) {
            System.out.println(e);
        }

        Iterator<Appointment> itr = appointments.iterator();

        if (weekRadio != null && weekRadio.isSelected()){
            for (; itr.hasNext();) {
                Appointment appointment = itr.next();
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
        } else if (monthRadio != null && monthRadio.isSelected()){
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
    /** Function creates the list of times to be used in the dropdowns for appointment mod/add. */
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
    /** Sets drop-downs on page. Fills customers, users, and contacts & sets the drop-downs to these lists. */
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
    /** Used to clear the form fields of the "add" form. */
    private void clearAddFormFields(){
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
    /** Insures that all data inputted into the add appointment form fields pass specified requirements. For each separate requirement that does not pass
     * a specific error is provided and the complete error message is returned.
     * @return a string which is the complete error message with a specific indication for each error found. */
    private String validateAdd() throws SQLException {
        titleField.setStyle(null);
        descriptionField.setStyle(null);
        locationField.setStyle(null);
        typeField.setStyle(null);
        contactDropD.setStyle(null);
        custDropD.setStyle(null);
        startDateP.setStyle(null);
        endDateP.setStyle(null);
        addStartTime.setStyle(null);
        addEndTime.setStyle(null);

        String errorM = "The following errors were found:\n\n";
        String timeErrorM = "The following schedule errors were found:\n\n";
        String noErrorM = null;
        Boolean err = false;
        Boolean schErr = false;

        if (titleField.getText() == ""){
            errorM += "- Title field must be populated.\n";
            titleField.setStyle("-fx-border-color: red");
            err = true;
        }
        if (descriptionField.getText() == ""){
            errorM += "- Description field must be populated.\n";
            descriptionField.setStyle("-fx-border-color: red");
            err = true;
        }
        if (locationField.getText() == ""){
            errorM += "- Location field must be populated.\n";
            locationField.setStyle("-fx-border-color: red");
            err = true;
        }
        if (typeField.getText() == ""){
            errorM += "- Type field must be populated.\n";
            typeField.setStyle("-fx-border-color: red");
            err = true;
        }
        if (contactDropD.getSelectionModel().getSelectedItem() == null){
            errorM += "- A contact must be selected.\n";
            contactDropD.setStyle("-fx-border-color: red");
            err = true;
        }
        if (custDropD.getSelectionModel().getSelectedItem() == null){
            errorM += "- A customer must be selected.\n";
            custDropD.setStyle("-fx-border-color: red");
            err = true;
        }
        if (userDropD.getSelectionModel().getSelectedItem() == null){
            errorM += "- A user must be selected.\n";
            userDropD.setStyle("-fx-border-color: red");
            err = true;
        }
        //____________________________TIME_________________
        ZoneOffset estOffset = ZoneId.of("US/Eastern").getRules().getOffset(Instant.now());
        LocalTime st = LocalTime.parse("08:00");
        LocalTime en = LocalTime.parse("22:00");
        LocalTime startBus = OffsetTime.of(st, estOffset).withOffsetSameInstant(CurrentSession.getOS()).toLocalTime();
        LocalTime endBus = OffsetTime.of(en, estOffset).withOffsetSameInstant(CurrentSession.getOS()).toLocalTime();

        if (startDateP.getValue() == null){
            errorM += "- A start date must be selected.\n";
            startDateP.setStyle("-fx-border-color: red");
            err = true;
        }

        if (endDateP.getValue() == null){
            errorM += "- An end time must be selected.\n";
            endDateP.setStyle("-fx-border-color: red");
            err = true;
        }
        if (addStartTime.getSelectionModel().getSelectedItem() == null){
            errorM += "- A start time must be selected.\n";
            addStartTime.setStyle("-fx-border-color: red");
            err = true;
        } else {
            if(addStartTime.getValue().isBefore(startBus) || addStartTime.getValue().isAfter(endBus)){
                timeErrorM += "- Start time must be between 8am EST & 10pm EST.\n";
                addStartTime.setStyle("-fx-border-color: red");
                schErr = true;
            }
        }
        if (addEndTime.getSelectionModel().getSelectedItem() == null){
            errorM += "- An end time must be selected.\n";
            addEndTime.setStyle("-fx-border-color: red");
            err = true;
        } else {
            if(addEndTime.getValue().isBefore(startBus) || addEndTime.getValue().isAfter(endBus)){
                timeErrorM += "- End time must be between 8am EST & 10pm EST.\n";
                addEndTime.setStyle("-fx-border-color: red");
                schErr = true;
            }
        }

        //Times Getting local date & time together
        LocalDateTime start = null;
        LocalDateTime end = null;
        if (startDateP.getValue() != null && addStartTime.getValue() != null){
            LocalDate startDate = startDateP.getValue();
            LocalTime startTime = addStartTime.getValue();
            start = LocalDateTime.of(startDate, startTime);
        }

        if (endDateP.getValue() != null && addEndTime.getValue() != null){
            LocalDate endDate = endDateP.getValue();
            LocalTime endTime = addEndTime.getValue();
            end = LocalDateTime.of(endDate, endTime);
        }

        if (start != null && end != null){
            if (start.isAfter(end)){
                timeErrorM += "- Start date & time must be before end date & time.\n";
                startDateP.setStyle("-fx-border-color: red");
                addStartTime.setStyle("-fx-border-color: red");
                endDateP.setStyle("-fx-border-color: red");
                addEndTime.setStyle("-fx-border-color: red");
                schErr = true;
            } else {
                ObservableList<Appointment> appointments = AppointmentDAO.getAllAppointments();
                for (Appointment appointment: appointments){
                    if (appointment.getCustomer() == custDropD.getValue().getId()){
                        if ((start.isBefore(appointment.getEnd()) && start.isAfter(appointment.getStart())) || (end.isAfter(appointment.getStart()) && end.isBefore(appointment.getEnd())) || (appointment.getEnd().isAfter(start) && appointment.getEnd().isBefore(end)) || (appointment.getStart().isAfter(start) && appointment.getStart().isBefore(end))) {
                            timeErrorM += "-Customer number: " + String.valueOf(appointment.getCustomer()) + " has a conflict from " + appointment.getStartFormat() + " to " + appointment.getEndFormat() + ".\n";
                            startDateP.setStyle("-fx-border-color: red");
                            addStartTime.setStyle("-fx-border-color: red");
                            endDateP.setStyle("-fx-border-color: red");
                            addEndTime.setStyle("-fx-border-color: red");
                            schErr = true;
                        }
                    }
                }
            }
        }

        if (!err && !schErr) {
            return noErrorM;
        } else if ( err && schErr) {
            return errorM + "\n\n" + timeErrorM;
        } else if (!err && schErr) {
            return timeErrorM;
        } else {
            return errorM;
        }
    }
    /** Insures that all data inputted into the modify appointment form fields pass specified requirements. For each separate requirement that does not pass
     * a specific error is provided and the complete error message is returned.
     * @return a string which is the complete error message with a specific indication for each error found. */
    private String validateMod() throws SQLException {
        modTitleField.setStyle(null);
        modDescriptionField.setStyle(null);
        modLocationField.setStyle(null);
        modTypeField.setStyle(null);
        modContactDropD.setStyle(null);
        modCustDropD.setStyle(null);
        modStartDateP.setStyle(null);
        modEndDateP.setStyle(null);
        modStartTime.setStyle(null);
        modEndTime.setStyle(null);

        String errorM = "The following errors were found:\n\n";
        String timeErrorM = "The following schedule errors were found:\n\n";
        String noErrorM = null;
        Boolean err = false;
        Boolean schErr = false;

        if (modTitleField.getText() == ""){
            errorM += "- Title field must be populated.\n";
            modTitleField.setStyle("-fx-border-color: red");
            err = true;
        }
        if (modDescriptionField.getText() == ""){
            errorM += "- Description field must be populated.\n";
            modDescriptionField.setStyle("-fx-border-color: red");
            err = true;
        }
        if (modLocationField.getText() == ""){
            errorM += "- Location field must be populated.\n";
            modLocationField.setStyle("-fx-border-color: red");
            err = true;
        }
        if (modTypeField.getText() == ""){
            errorM += "- Type field must be populated.\n";
            modTypeField.setStyle("-fx-border-color: red");
            err = true;
        }
        if (modContactDropD.getSelectionModel().getSelectedItem() == null){
            errorM += "- A contact must be selected.\n";
            modContactDropD.setStyle("-fx-border-color: red");
            err = true;
        }
        if (modCustDropD.getSelectionModel().getSelectedItem() == null){
            errorM += "- A customer must be selected.\n";
            modCustDropD.setStyle("-fx-border-color: red");
            err = true;
        }
        if (modUserDropD.getSelectionModel().getSelectedItem() == null){
            errorM += "- A user must be selected.\n";
            modUserDropD.setStyle("-fx-border-color: red");
            err = true;
        }

        //___________________TIME__________________________________
        ZoneOffset estOffset = ZoneId.of("US/Eastern").getRules().getOffset(Instant.now());
        LocalTime st = LocalTime.parse("08:00");
        LocalTime en = LocalTime.parse("22:00");
        LocalTime startBus = OffsetTime.of(st, estOffset).withOffsetSameInstant(CurrentSession.getOS()).toLocalTime();
        LocalTime endBus = OffsetTime.of(en, estOffset).withOffsetSameInstant(CurrentSession.getOS()).toLocalTime();

        if (modStartDateP.getValue() == null){
            errorM += "- A start date must be selected.\n";
            modStartDateP.setStyle("-fx-border-color: red");
            err = true;
        }

        if (modEndDateP.getValue() == null){
            errorM += "- An end time must be selected.\n";
            modEndDateP.setStyle("-fx-border-color: red");
            err = true;
        }

        if (modStartTime.getSelectionModel().getSelectedItem() == null){
            errorM += "- A start time must be selected.\n";
            modStartTime.setStyle("-fx-border-color: red");
            err = true;
        } else {
            if(modStartTime.getValue().isBefore(startBus) || modStartTime.getValue().isAfter(endBus)){
                timeErrorM += "- Start time must be between 8am EST & 10pm EST.\n";
                modStartTime.setStyle("-fx-border-color: red");
                schErr = true;
            }
        }

        if (modEndTime.getSelectionModel().getSelectedItem() == null){
            errorM += "- An end time must be selected.\n";
            modEndTime.setStyle("-fx-border-color: red");
            err = true;
        } else {
            if(modEndTime.getValue().isBefore(startBus) || modEndTime.getValue().isAfter(endBus)){
                timeErrorM += "- End time must be between 8am EST & 10pm EST.\n";
                modEndTime.setStyle("-fx-border-color: red");
                schErr = true;
            }
        }

        //Times Getting local date & time together
        LocalDateTime start = null;
        LocalDateTime end = null;
        if (modStartDateP.getValue() != null && modStartTime.getValue() != null){
            LocalDate startDate = modStartDateP.getValue();
            LocalTime startTime = modStartTime.getValue();
            start = LocalDateTime.of(startDate, startTime);
        }

        if (modEndDateP.getValue() != null && modEndDateP.getValue() != null){
            LocalDate endDate = modEndDateP.getValue();
            LocalTime endTime = modEndTime.getValue();
            end = LocalDateTime.of(endDate, endTime);
        }

        if (start != null && end != null){
            if (start.isAfter(end)){
                timeErrorM += "- Start date & time must be before end date & time.\n";
                modStartDateP.setStyle("-fx-border-color: red");
                modStartTime.setStyle("-fx-border-color: red");
                modEndDateP.setStyle("-fx-border-color: red");
                modEndTime.setStyle("-fx-border-color: red");
                schErr = true;
            } else {
                ObservableList<Appointment> appointments = AppointmentDAO.getAllAppointments();
                for (Appointment appointment: appointments){
                    if (appointment.getCustomer() == modCustDropD.getValue().getId() && appointment.getId() != Integer.parseInt(modIdField.getText())){
                        if ((start.isBefore(appointment.getEnd()) && start.isAfter(appointment.getStart())) || (end.isAfter(appointment.getStart()) && end.isBefore(appointment.getEnd())) || (appointment.getEnd().isAfter(start) && appointment.getEnd().isBefore(end)) || (appointment.getStart().isAfter(start) && appointment.getStart().isBefore(end))) {
                            timeErrorM += "-Customer number: " + String.valueOf(appointment.getCustomer()) + " has a conflict from " + appointment.getStartFormat() + " to " + appointment.getEndFormat() + ".\n";
                            modStartDateP.setStyle("-fx-border-color: red");
                            modStartTime.setStyle("-fx-border-color: red");
                            modEndDateP.setStyle("-fx-border-color: red");
                            modEndTime.setStyle("-fx-border-color: red");
                            schErr = true;
                        }
                    }
                }
            }
        }

        if (!err && !schErr) {
            return noErrorM;
        } else if ( err && schErr) {
            return errorM + "\n\n" + timeErrorM;
        } else if (!err && schErr) {
            return timeErrorM;
        } else {
            return errorM;
        }
    }
}
