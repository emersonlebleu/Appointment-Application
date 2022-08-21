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
        clearAddFormFields();
        homePane.toFront();
    }

    public void onCancelAdd(ActionEvent actionEvent) {
        clearAddFormFields();
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
            try {
                ObservableList<Appointment> appointments = AppointmentDAO.getAllAppointments();
                for (Appointment appointment: appointments) {
                    if (appointment.getCustomer() == selectedCustomer.getId()){
                        AppointmentDAO.deleteAppointment(appointment.getId());
                    }
                }
            } catch (Exception e) { System.out.println(e);}
            try {
                CustomerDAO.deleteCustomer(selectedCustomer.getId());
            } catch (Exception e) { System.out.println(e);}
        }
        setCustTable();
    }

    public void mouseOvAdd(MouseEvent mouseEvent) {
    }

    public void mouseOutAdd(MouseEvent mouseEvent) {
    }

    public void mouseOvMod(MouseEvent mouseEvent) {
    }

    public void mouseOutMod(MouseEvent mouseEvent) {
    }

    public void mouseOvDel(MouseEvent mouseEvent) {
    }

    public void mouseOutDel(MouseEvent mouseEvent) {
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
    /** Set divistions drop-downs on page */
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

    public void clearAddFormFields(){
        nameField.clear();
        addressField.clear();
        postalField.clear();
        phoneField.clear();
        countryDropD.getSelectionModel().clearSelection();
        firstLevDropD.setItems(null);
    }

    public void countryDropDChange(ActionEvent actionEvent) {
        if (countryDropD.getSelectionModel().getSelectedItem() != null){
            selCountryId = countryDropD.getSelectionModel().getSelectedItem().getId();
        } else {
            selCountryId = null;
        }
        refreshDivisions();
    }
}
