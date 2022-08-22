package controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
    }
}
