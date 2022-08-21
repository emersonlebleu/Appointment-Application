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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCustTable();
        refreshCountries();
    }

    public void onSaveMod(ActionEvent actionEvent) {
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
    }

    public void onCancelMod(ActionEvent actionEvent) {
        homePane.toFront();
    }

    public void onSaveAdd(ActionEvent actionEvent) {
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
    }

    public void onCancelAdd(ActionEvent actionEvent) {
        clearFormFields();
        homePane.toFront();
    }

    public void add_cust(ActionEvent actionEvent) {
        addPane.toFront();
    }

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

    public void mouseOvAdd(MouseEvent mouseEvent) {
        startStyle = addCustBtn.getStyle();
        addCustBtn.setStyle("-fx-background-color: #2F334B;");
    }

    public void mouseOutAdd(MouseEvent mouseEvent) {
        addCustBtn.setStyle(startStyle);
    }

    public void mouseOvMod(MouseEvent mouseEvent) {
        startStyle = modCustBtn.getStyle();
        modCustBtn.setStyle("-fx-background-color: #2F334B;");
    }

    public void mouseOutMod(MouseEvent mouseEvent) {
        modCustBtn.setStyle(startStyle);
    }

    public void mouseOvDel(MouseEvent mouseEvent) {
        startStyle = delCustBtn.getStyle();
        delCustBtn.setStyle("-fx-background-color: #2F334B;");
    }

    public void mouseOutDel(MouseEvent mouseEvent) {
        delCustBtn.setStyle(startStyle);
    }

    //------------------------------------------------------Functions Used-----------------------------------
    private ObservableList<Division> divisions;
    private ObservableList<Country> countries;

    /** Function refreshes date in the customers table */
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

    /** Set country drop-downs on page */
    private void refreshCountries(){
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
    /** Set divisions drop-downs on page */
    private void refreshDivisions(){
        try {
            divisions = DivisionDAO.getAllDivisions();

            if (selCountryId == null) {
                firstLevDropD.setItems(null);
                firstLevDropD.setVisibleRowCount(5);

                modFirstLevDropD.setItems(null);
                modFirstLevDropD.setVisibleRowCount(5);
            } else if (!(divisions.size() == 0)) {
                Iterator<Division> itr = divisions.iterator();
                for (Iterator<Division> it = itr; it.hasNext();){
                    Division division = it.next();
                    if (!(division.getCountry() == selCountryId)){
                        itr.remove();
                    }
                }
                firstLevDropD.setItems(divisions);
                firstLevDropD.setVisibleRowCount(5);

                modFirstLevDropD.setItems(divisions);
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

    public void countryDropDChangeAdd(ActionEvent actionEvent) {
        if (countryDropD.getSelectionModel().getSelectedItem() != null){
            selCountryId = countryDropD.getSelectionModel().getSelectedItem().getId();
        } else {
            selCountryId = null;
        }
        refreshDivisions();
    }

    public void countryDropDChangeMod(ActionEvent actionEvent) {
        if (modCountryDropD.getSelectionModel().getSelectedItem() != null){
            selCountryId = modCountryDropD.getSelectionModel().getSelectedItem().getId();
        } else {
            selCountryId = null;
        }
        refreshDivisions();
    }
}
