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
import javafx.stage.StageStyle;
import model.*;
import utilities.*;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Customers implements Initializable {
    public StackPane custStack;
    public AnchorPane modPane;
    public TextField modIdField;
    public TextField modNameField;
    public TextField modAddressField;
    public TextField modPostalField;
    public TextField modPhoneField;
    public ComboBox<Country> modCountryDropD;
    public ComboBox<Division> modFirstLevDropD;
    public Button saveMod;
    public Button cancelMod;
    public AnchorPane addPane;
    public TextField idField;
    public TextField nameField;
    public TextField addressField;
    public TextField postalField;
    public TextField phoneField;
    public ComboBox<Country> countryDropD;
    public ComboBox<Division> firstLevDropD;
    public Button saveAdd;
    public Button cancelAdd;
    public AnchorPane homePane;
    public TableView custTable;
    public TableColumn idCol;
    public TableColumn nameCol;
    public TableColumn addressCol;
    public TableColumn postalCol;
    public TableColumn phoneCol;
    public TableColumn firstLevCol;
    public Button addCustBtn;
    public Button modCustBtn;
    public Button delCustBtn;
    private model.Customer selectedCustomer;
    private static String startStyle;
    private static Integer selCountryId = null;
    /** The initialize function for this controller. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCustTable();
        refreshCountries();
    }
    /** Runs the validateMod function and if function doesn't return an error message the information from the
     * form is saved into a customer object and then passed to the customerDAO.updateCustomer() function
     * to be stored in the database. If the validateMod function returns an error message an alert is generated
     * with the error(s) from the validation.*/
    public void onSaveMod(ActionEvent actionEvent) {
        if (validateMod() == null){
            Integer id = Integer.valueOf(modIdField.getText());
            String name = modNameField.getText();
            String address = modAddressField.getText();
            String postal = modPostalField.getText();
            String phone = modPhoneField.getText();
            Integer firstLevId = modFirstLevDropD.getValue().getId();

            model.Customer newCust = new Customer(id, name, address, postal, phone, firstLevId);
            try {
                CustomerDAO.updateCustomer(newCust);
            } catch (Exception e) {
                System.out.println(e);
            }

            setCustTable();
            clearFormFields();
            homePane.toFront();

            modNameField.setStyle(null);
            modAddressField.setStyle(null);
            modPostalField.setStyle(null);
            modPhoneField.setStyle(null);
            modCountryDropD.setStyle(null);
            modFirstLevDropD.setStyle(null);
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
    /** If cancel modification then home page moves to front. */
    public void onCancelMod(ActionEvent actionEvent) {
        homePane.toFront();
    }
    /** Runs the validateAdd function and if function doesn't return an error message the information from the
     * form is saved into a customer object and then passed to the customerDAO.addCustomer() function
     * to be stored in the database. If the validateAdd function returns an error message an alert is generated
     * with the error(s) from the validation.*/
    public void onSaveAdd(ActionEvent actionEvent) {
        if (validateAdd() == null){
            String name = nameField.getText();
            String address = addressField.getText();
            String postal = postalField.getText();
            String phone = phoneField.getText();
            Integer firstLevId = firstLevDropD.getValue().getId();

            model.Customer newCust = new Customer(name, address, postal, phone, firstLevId);
            try {
                CustomerDAO.addCustomer(newCust);
            } catch (Exception e) {
                System.out.println(e);
            }

            setCustTable();
            clearFormFields();
            homePane.toFront();

            nameField.setStyle(null);
            addressField.setStyle(null);
            postalField.setStyle(null);
            phoneField.setStyle(null);
            countryDropD.setStyle(null);
            firstLevDropD.setStyle(null);
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
    /** If cancel add then form fields are reset and home page moves to front. */
    public void onCancelAdd(ActionEvent actionEvent) {
        clearFormFields();
        homePane.toFront();
    }
    /** Moves the add pane to front. */
    public void add_cust(ActionEvent actionEvent) {
        addPane.toFront();
    }
    /** Moves the mod pane to front and populates the form fields if no customer selected generates an alert
     * error message. Utilizes the selected customer from the table to populate the form fields appropriately. */
    public void mod_cust(ActionEvent actionEvent) {
        selectedCustomer = (Customer) custTable.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null) {
            //----------------No Selection Error--------------------//
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UTILITY);
            alert.setContentText("Please make a selection.");

            alert.showAndWait();
        } else {
            refreshCountries();
            modIdField.setText(String.valueOf(selectedCustomer.getId()));
            modNameField.setText(selectedCustomer.getName());
            modAddressField.setText(selectedCustomer.getAddress());
            modPostalField.setText(selectedCustomer.getPostalCode());
            modPhoneField.setText(selectedCustomer.getPhone());

            ObservableList<Country> countries = FXCollections.observableArrayList();
            ObservableList<Division> divisions = FXCollections.observableArrayList();

            try {
              countries = CountryDAO.getAllCountries();
              divisions = DivisionDAO.getAllDivisions();
            } catch (Exception e) {}

            Division currDiv = null;
            Country currCountry = null;

            for (Division division: divisions){
                if (division.getId() == selectedCustomer.getDivision()){
                    currDiv = division;
                }
            }

            for (Country country: countries){
                if (country.getId() == currDiv.getCountry()){
                    currCountry = country;
                }
            }

            refreshCountries();
            modCountryDropD.getSelectionModel().select(currCountry);
            selCountryId = currCountry.getId();
            modFirstLevDropD.getSelectionModel().select(currDiv);
            refreshDivisions();

            modPane.toFront();
        }
    }
    /** If a customer is selected that customer id is passed to the delete function within the customerDAO.
     * Additionally, an alert is generated showing the user what customer was deleted if successful.
     * If a customer is not selected an error message popup is generated. */
    public void delete_cust(ActionEvent actionEvent) {
        selectedCustomer = (Customer) custTable.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null) {
            //----------------No Selection Error--------------------//
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UTILITY);
            alert.setContentText("Please make a selection to delete a customer.");

            alert.showAndWait();
        } else {
            //--------------Remove Confirmation Box----------------------//
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you'd like to delete this customer? All associated appointments will also be deleted.");
            ButtonType buttonTypeYes = new ButtonType("Yes");
            ButtonType buttonTypeNo = new ButtonType("No");

            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeYes) {
                Integer numAppt = 0;
                try {
                    ObservableList<Appointment> appointments = AppointmentDAO.getAllAppointments();
                    for (Appointment appointment: appointments) {
                        if (appointment.getCustomer() == selectedCustomer.getId()){
                            AppointmentDAO.deleteAppointment(appointment.getId());
                            numAppt ++;
                        }
                    }
                } catch (Exception e) { System.out.println(e);}
                try {
                    CustomerDAO.deleteCustomer(selectedCustomer.getId());

                    //----------------Delete Customer--------------------//
                    Alert notification = new Alert(Alert.AlertType.INFORMATION);
                    notification.setTitle("Delete Customer Notification");
                    notification.setHeaderText(null);
                    notification.initStyle(StageStyle.UTILITY);
                    notification.setContentText("Deleted customer named \"" + selectedCustomer.getName() + "\" & " + String.valueOf(numAppt) + " associated appointments.");

                    notification.showAndWait();
                } catch (Exception e) { System.out.println(e);}
            }
            setCustTable();
            }
    }
    /** Functionality for styling button on mouse enter/exit. */
    public void mouseOvAdd(MouseEvent mouseEvent) {
        startStyle = addCustBtn.getStyle();
        addCustBtn.setStyle("-fx-background-color: #2F334B;");
    }
    /** Functionality for styling button on mouse enter/exit. */
    public void mouseOutAdd(MouseEvent mouseEvent) {
        addCustBtn.setStyle(startStyle);
    }
    /** Functionality for styling button on mouse enter/exit. */
    public void mouseOvMod(MouseEvent mouseEvent) {
        startStyle = modCustBtn.getStyle();
        modCustBtn.setStyle("-fx-background-color: #2F334B;");
    }
    /** Functionality for styling button on mouse enter/exit. */
    public void mouseOutMod(MouseEvent mouseEvent) {
        modCustBtn.setStyle(startStyle);
    }
    /** Functionality for styling button on mouse enter/exit. */
    public void mouseOvDel(MouseEvent mouseEvent) {
        startStyle = delCustBtn.getStyle();
        delCustBtn.setStyle("-fx-background-color: #2F334B;");
    }
    /** Functionality for styling button on mouse enter/exit. */
    public void mouseOutDel(MouseEvent mouseEvent) {
        delCustBtn.setStyle(startStyle);
    }
    /** Detects dropdown change on country for add form and repopulates the divisions.*/
    public void countryDropDChangeAdd(ActionEvent actionEvent) {
        if (countryDropD.getSelectionModel().getSelectedItem() != null){
            selCountryId = countryDropD.getSelectionModel().getSelectedItem().getId();
        } else {
            selCountryId = null;
        }
        refreshDivisions();
    }
    /** Detects dropdown change on country for mod form and repopulates the divisions.*/
    public void countryDropDChangeMod(ActionEvent actionEvent) {
        if (modCountryDropD.getSelectionModel().getSelectedItem() != null){
            selCountryId = modCountryDropD.getSelectionModel().getSelectedItem().getId();
        } else {
            selCountryId = null;
        }
        refreshDivisions();
    }

    //-------------------------------------------------------------------FUNCTION DECLARATIONS--------------------------------------------------
    /** Function refreshes data in the customers table */
    private void setCustTable(){
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        try {
            customers = CustomerDAO.getAllCustomers();
        } catch (Exception e) {
            System.out.println(e);
        }

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        firstLevCol.setCellValueFactory(new PropertyValueFactory<>("division"));

        custTable.setItems(customers);
    }
    /** Set country drop-downs on add and mod forms. */
    private void refreshCountries(){
        ObservableList<Country> countries;
        try {
            countries = CountryDAO.getAllCountries();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //Set the choice box options add page
        countryDropD.setItems(countries);
        countryDropD.setVisibleRowCount(5);

        //Set the choice box options mod page
        modCountryDropD.setItems(countries);
        modCountryDropD.setVisibleRowCount(5);
    }
    /** <b>LAMBDA FUNCTION PRESENT***</b> This overall function Set divisions drop-downs on page. The lambda function here allows this function to assign all divisions for which the
     * country matches the selected country into an observable array list of the appropriate divisions for the selected countries. It has reduced the length of the
     * block of code in this case moderately. */
    private void refreshDivisions(){
        ObservableList<Division> matches;
        try {
            ObservableList<Division> divisions = DivisionDAO.getAllDivisions();

            if (selCountryId == null) {
                firstLevDropD.setItems(null);
                firstLevDropD.setVisibleRowCount(5);

                modFirstLevDropD.setItems(null);
                modFirstLevDropD.setVisibleRowCount(5);
            } else if (!(divisions.size() == 0)) {
                // Lambda here***
                matches = divisions.stream().filter(division -> division.getCountry() == selCountryId).collect(Collectors.toCollection(FXCollections::observableArrayList));

                firstLevDropD.setItems(matches);
                firstLevDropD.setVisibleRowCount(5);

                modFirstLevDropD.setItems(matches);
                modFirstLevDropD.setVisibleRowCount(5);
            } else {
                firstLevDropD.setItems(null);
                firstLevDropD.setVisibleRowCount(5);

                modFirstLevDropD.setItems(null);
                modFirstLevDropD.setVisibleRowCount(5);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /** Clears the fields of both forms. */
    public void clearFormFields(){
        nameField.clear();
        addressField.clear();
        postalField.clear();
        phoneField.clear();
        countryDropD.getSelectionModel().clearSelection();
        firstLevDropD.setItems(null);

        modNameField.clear();
        modAddressField.clear();
        modPostalField.clear();
        modPhoneField.clear();
        modCountryDropD.getSelectionModel().clearSelection();
        modFirstLevDropD.setItems(null);
    }
    /** Assures that information within the add form is valid. Provides error message for each error found or
     * null if none are found.
     * @return a string the error message. */
    private String validateAdd(){
        nameField.setStyle(null);
        addressField.setStyle(null);
        postalField.setStyle(null);
        phoneField.setStyle(null);
        countryDropD.setStyle(null);
        firstLevDropD.setStyle(null);

        String errorM = "The following errors were found:\n\n";
        String noErrorM = null;
        Boolean err = false;

        if (nameField.getText() == ""){
            errorM += "- Name field must be populated.\n";
            nameField.setStyle("-fx-border-color: red");
            err = true;
        }
        if (addressField.getText() == ""){
            errorM += "- Address field must be populated.\n";
            addressField.setStyle("-fx-border-color: red");
            err = true;
        }
        if (postalField.getText() == ""){
            errorM += "- Postal Code field must be populated.\n";
            postalField.setStyle("-fx-border-color: red");
            err = true;
        }
        if (phoneField.getText() == ""){
            errorM += "- Phone field must be populated.\n";
            phoneField.setStyle("-fx-border-color: red");
            err = true;
        }
        if (countryDropD.getSelectionModel().getSelectedItem() == null){
            errorM += "- A country must be selected.\n";
            countryDropD.setStyle("-fx-border-color: red");
            err = true;
        }
        if (firstLevDropD.getSelectionModel().getSelectedItem() == null){
            errorM += "- A first level division must be selected.\n";
            firstLevDropD.setStyle("-fx-border-color: red");
            err = true;
        }
        if (!err) {
            return noErrorM;
        } else {
            return errorM;
        }
    }
    /** Assures that information within the mod form is valid. Provides error message for each error found or
     * null if none are found.
     * @return a string the error message. */
    private String validateMod(){
        modNameField.setStyle(null);
        modAddressField.setStyle(null);
        modPostalField.setStyle(null);
        modPhoneField.setStyle(null);
        modCountryDropD.setStyle(null);
        modFirstLevDropD.setStyle(null);

        String errorM = "The following errors were found:\n\n";
        String noErrorM = null;
        Boolean err = false;

        if (modNameField.getText() == ""){
            errorM += "- Name field must be populated.\n";
            modNameField.setStyle("-fx-border-color: red");
            err = true;
        }
        if (modAddressField.getText() == ""){
            errorM += "- Address field must be populated.\n";
            modAddressField.setStyle("-fx-border-color: red");
            err = true;
        }
        if (modPostalField.getText() == ""){
            errorM += "- Postal Code field must be populated.\n";
            modPostalField.setStyle("-fx-border-color: red");
            err = true;
        }
        if (modPhoneField.getText() == ""){
            errorM += "- Phone field must be populated.\n";
            modPhoneField.setStyle("-fx-border-color: red");
            err = true;
        }
        if (modCountryDropD.getSelectionModel().getSelectedItem() == null){
            errorM += "- A country must be selected.\n";
            modCountryDropD.setStyle("-fx-border-color: red");
            err = true;
        }
        if (modFirstLevDropD.getSelectionModel().getSelectedItem() == null){
            errorM += "- A first level division must be selected.\n";
            modFirstLevDropD.setStyle("-fx-border-color: red");
            err = true;
        }

        if (!err) {
            return noErrorM;
        } else {
            return errorM;
        }
    }
}
