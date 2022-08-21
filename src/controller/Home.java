package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.StageStyle;
import model.Appointment;
import utilities.AppointmentDAO;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/** Main window controller. */
public class Home implements Initializable {
    public Button apptButton;
    public Label userNameLabel;
    public Button custButton;
    public Button reportButton;
    public AnchorPane contactsPane;
    public AnchorPane reportsPane;
    public AnchorPane appointmentsPane;
    public StackPane stackPane;
    public AnchorPane nav;
    public AnchorPane homeCanvas;
    public AnchorPane header;
    public Label appTitle;
    public Label pageLabel;

    private static String startStyle;

    /** Initializer called when page is loaded. Changes username label to username from login.
     * sets the active page indication on button. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userNameLabel.setText(Login.currUser.getName());
        apptButton.setStyle("-fx-background-color: #8E94B8;");
    }

    /** Appointment button pressed. Brings appropriate frame to front, changes page label, sets
     * button styles appropriately to display active current page via button color. */
    public void apptBtnPress(ActionEvent actionEvent) {
        appointmentsPane.toFront();
        pageLabel.setText("Appointments");
        reportButton.setStyle("-fx-background-color: #3F4464;");
        custButton.setStyle("-fx-background-color: #3F4464;");
        apptButton.setStyle("-fx-background-color: #8E94B8;");
        startStyle = apptButton.getStyle();
    }
    /** Customers button pressed. Brings appropriate frame to front, changes page label, sets
     * button styles appropriately to display active current page via button color. */
    public void custBtnPress(ActionEvent actionEvent) {
        contactsPane.toFront();
        pageLabel.setText("Customers");

        apptButton.setStyle("-fx-background-color: #3F4464;");
        reportButton.setStyle("-fx-background-color: #3F4464;");
        custButton.setStyle("-fx-background-color: #8E94B8;");
        startStyle = custButton.getStyle();
    }
    /** Report button pressed. Brings appropriate frame to front, changes page label, sets
     * button styles appropriately to display active current page via button color. */
    public void reportBtnPress(ActionEvent actionEvent) {
        reportsPane.toFront();

        pageLabel.setText("Reports");

        custButton.setStyle("-fx-background-color: #3F4464;");
        apptButton.setStyle("-fx-background-color: #3F4464;");
        reportButton.setStyle("-fx-background-color: #8E94B8;");
        startStyle = reportButton.getStyle();
    }
    /** Mouse over gets current style & changes color of button. */
    public void mouseOvAppt(MouseEvent mouseEvent) {
        startStyle = apptButton.getStyle();
        apptButton.setStyle("-fx-background-color: #2F334B;");
    }
    /** Mouse out changes color of button back to initial style. */
    public void mouseOutApt(MouseEvent mouseEvent) {
        apptButton.setStyle(startStyle);
    }
    /** Mouse over gets current style & changes color of button. */
    public void mouseOvCust(MouseEvent mouseEvent) {
        startStyle = custButton.getStyle();
        custButton.setStyle("-fx-background-color: #2F334B;");
    }
    /** Mouse out changes color of button back to initial style. */
    public void mouseOutCust(MouseEvent mouseEvent) {
        custButton.setStyle(startStyle);
    }
    /** Mouse over gets current style & changes color of button. */
    public void mouseOvReport(MouseEvent mouseEvent) {
        startStyle = reportButton.getStyle();
        reportButton.setStyle("-fx-background-color: #2F334B;");
    }
    /** Mouse out changes color of button back to initial style. */
    public void mouseOutReport(MouseEvent mouseEvent) {
        reportButton.setStyle(startStyle);
    }
}
