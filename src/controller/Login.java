package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

/** Login Controller. */
public class Login implements Initializable {

    public TextField usernameField;
    public TextField passwordField;
    public Button loginButton;
    public Label usernameLabel;
    public Label passwordLabel;
    public Label tzLabel;
    public Label langLabel;
    public Label tzText;
    public Label langText;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Login Initialized");
    }

    /** Login button pressed, gather username and password and validate against DB.
     * Loads the main page if successfull displays error if not. */
    public void onLogin(ActionEvent actionEvent) {

    }

    /** Change color on mouse over. */
    public void mouseOver(MouseEvent mouseEvent) {
        loginButton.setStyle("-fx-background-color: #EFA857");
    }
    /** Change color to default on mouse out. */
    public void mouseOut(MouseEvent mouseEvent) {
        loginButton.setStyle("-fx-background-color: #ED9B40");
    }
}
