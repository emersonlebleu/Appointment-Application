package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
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

    /** Window initializer called when window is loaded. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Main Initialized");
    }

    public void apptBtnPress(ActionEvent actionEvent) {
        appointmentsPane.toFront();
        pageLabel.setText("Appointments");
    }

    public void custBtnPress(ActionEvent actionEvent) {
        contactsPane.toFront();
        pageLabel.setText("Contacts");
    }

    public void reportBtnPress(ActionEvent actionEvent) {
        reportsPane.toFront();
        pageLabel.setText("Reports");
    }

    public void mouseOvAppt(MouseEvent mouseEvent) {
    }

    public void mouseOutApt(MouseEvent mouseEvent) {
    }

    public void mouseOvCust(MouseEvent mouseEvent) {
    }

    public void mouseOutCust(MouseEvent mouseEvent) {
    }

    public void mouseOvReport(MouseEvent mouseEvent) {
    }

    public void mouseOutReport(MouseEvent mouseEvent) {
    }
}
