package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import utilities.State;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** Main window controller. */
public class Home implements Initializable {
    /** The appointment page button. */
    public Button apptButton;
    /** Label for the current username. */
    public Label userNameLabel;
    /** The customer page button. */
    public Button custButton;
    /** The report page button. */
    public Button reportButton;
    /** The container for customer content. */
    public AnchorPane customerPane;
    /** The container for reports content. */
    public AnchorPane reportsPane;
    /** The container for appointments content. */
    public AnchorPane appointmentsPane;
    /** Overarching stack pane for three main view content containment. */
    public StackPane stackPane;
    /** Container for main navigation buttons. */
    public AnchorPane nav;
    /** Main container. */
    public AnchorPane homeCanvas;
    /** Header section displays current page and title. */
    public AnchorPane header;
    /** Title for app. */
    public Label appTitle;
    /** Lable for current page selection. */
    public Label pageLabel;
    /** Used to store the initial state of style for an element. Used for the button animations. */
    private static String startStyle;

    /** Initializer called when page is loaded. Changes username label to username from login.
     * sets the active page indication on button. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userNameLabel.setText(Login.currUser.getName());
        apptButton.setStyle("-fx-background-color: #8E94B8;");

        if (State.infront != null && State.infront.equals("Appointments")){
            pageLabel.setText("Appointments");
            reportButton.setStyle("-fx-background-color: #3F4464;");
            custButton.setStyle("-fx-background-color: #3F4464;");
            apptButton.setStyle("-fx-background-color: #8E94B8;");
            startStyle = apptButton.getStyle();
            appointmentsPane.toFront();
        } else if (State.infront != null && State.infront.equals("Customers")) {
            pageLabel.setText("Customers");

            apptButton.setStyle("-fx-background-color: #3F4464;");
            reportButton.setStyle("-fx-background-color: #3F4464;");
            custButton.setStyle("-fx-background-color: #8E94B8;");
            startStyle = custButton.getStyle();
            customerPane.toFront();
        } else if (State.infront != null && State.infront.equals("Reports")){
            pageLabel.setText("Reports");

            custButton.setStyle("-fx-background-color: #3F4464;");
            apptButton.setStyle("-fx-background-color: #3F4464;");
            reportButton.setStyle("-fx-background-color: #8E94B8;");
            startStyle = reportButton.getStyle();
            reportsPane.toFront();
        } else {
            appointmentsPane.toFront();
        }
    }

    /** Appointment button pressed. Reloads the view in order to reload all data in all tables/forms. Changes
     * the state.infront variable to appointments in order to have the appointments pane be in front. */
    public void apptBtnPress(ActionEvent actionEvent) throws IOException {
        State.infront = "Appointments";
        Parent root = FXMLLoader.load(getClass().getResource("/view/home.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("IMS: Main");
        stage.setScene(scene);

        stage.show();
    }
    /** Customers button pressed. Reloads the view in order to reload all data in all tables/forms. Changes
     * the state.infront variable to customers in order to have the appointments pane be in front. */
    public void custBtnPress(ActionEvent actionEvent) throws IOException {
        State.infront = "Customers";
        Parent root = FXMLLoader.load(getClass().getResource("/view/home.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("IMS: Main");
        stage.setScene(scene);

        stage.show();
    }
    /** Report button pressed. Reloads the view in order to reload all data in all tables/forms. Changes
     * the state.infront variable to reports in order to have the appointments pane be in front. */
    public void reportBtnPress(ActionEvent actionEvent) throws IOException {
        State.infront = "Reports";
        Parent root = FXMLLoader.load(getClass().getResource("/view/home.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("IMS: Main");
        stage.setScene(scene);

        stage.show();
    }
    /** Mouse over gets current style and changes color of button. */
    public void mouseOvAppt(MouseEvent mouseEvent) {
        startStyle = apptButton.getStyle();
        apptButton.setStyle("-fx-background-color: #2F334B;");
    }
    /** Mouse out changes color of button back to initial style. */
    public void mouseOutApt(MouseEvent mouseEvent) {
        apptButton.setStyle(startStyle);
    }
    /** Mouse over gets current style and changes color of button. */
    public void mouseOvCust(MouseEvent mouseEvent) {
        startStyle = custButton.getStyle();
        custButton.setStyle("-fx-background-color: #2F334B;");
    }
    /** Mouse out changes color of button back to initial style. */
    public void mouseOutCust(MouseEvent mouseEvent) {
        custButton.setStyle(startStyle);
    }
    /** Mouse over gets current style and changes color of button. */
    public void mouseOvReport(MouseEvent mouseEvent) {
        startStyle = reportButton.getStyle();
        reportButton.setStyle("-fx-background-color: #2F334B;");
    }
    /** Mouse out changes color of button back to initial style. */
    public void mouseOutReport(MouseEvent mouseEvent) {
        reportButton.setStyle(startStyle);
    }
}
