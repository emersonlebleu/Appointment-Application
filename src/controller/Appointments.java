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
import utilities.AppointmentDAO;

import java.net.URL;
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
    public ChoiceBox contactDropD;
    public DatePicker startDateP;
    public DatePicker endDateP;
    public ChoiceBox custDropD;
    public ChoiceBox userDropD;
    public TextField startHourField;
    public TextField startMinField;
    public TextField endHourField;
    public TextField endMinField;
    public ChoiceBox amPMStart;
    public ChoiceBox amPMEnd;


    private ObservableList<model.Appointment> appointments = FXCollections.observableArrayList();
    private model.Appointment selectedAppointment;
    private static String startStyle;

    public ObservableList<String> amPM = FXCollections.observableArrayList("AM", "PM");
    public ObservableList<String> moWK = FXCollections.observableArrayList("All", "Month", "Week");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        try {
            appointments = AppointmentDAO.getAllAppointments();
            for (model.Appointment appointment: appointments){
                System.out.println(appointment.getDescription());
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerCol.setCellValueFactory(new PropertyValueFactory<>("customer"));
        userCol.setCellValueFactory(new PropertyValueFactory<>("user"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));

        apptTable.setItems(appointments);

        //Set the choice box options
        amPMStart.getItems().addAll(amPM);
        amPMEnd.getItems().addAll(amPM);
        apptViewPicker.getItems().addAll(moWK);
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
        addPane.toBack();
    }

    public void onSaveAdd(ActionEvent actionEvent) {
    }
}
