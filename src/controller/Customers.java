package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import model.Country;
import model.Division;

import java.net.URL;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onSaveMod(ActionEvent actionEvent) {
    }

    public void onCancelMod(ActionEvent actionEvent) {
    }

    public void onSaveAdd(ActionEvent actionEvent) {
    }

    public void onCancelAdd(ActionEvent actionEvent) {
    }

    public void add_cust(ActionEvent actionEvent) {
    }

    public void mod_cust(ActionEvent actionEvent) {
    }

    public void delete_cust(ActionEvent actionEvent) {
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

}
