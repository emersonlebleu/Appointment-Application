package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Report;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        genTableData();
        setTables();

    }
}
