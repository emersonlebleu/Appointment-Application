package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utilities.Local;
import utilities.LoginQuery;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Objects;
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

    /** Initialize login, gets rb for french if the system language is French. Uses .properties to translate
     * text into appropriate French translation for any user text. Gets the time zone and language and assigns
     * the appropriate labels to the string values for displaying. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (Locale.getDefault().getLanguage().equals("fr")){
            String filename = "utilities/lang";
            ResourceBundle rb = ResourceBundle.getBundle(filename, Locale.getDefault());

            usernameLabel.setText(rb.getString("username"));
            passwordLabel.setText(rb.getString("password"));
            tzLabel.setText(rb.getString("timezone"));
            langLabel.setText(rb.getString("language"));
            loginButton.setText(rb.getString("login"));
        }

        tzText.setText(Local.getZone().toString());
        langText.setText(Local.getLangString());
    }

    /** Login button pressed, gather username and password and validate against DB.
     * Loads the main page if successfull, displays error if not. */
    public void onLogin(ActionEvent actionEvent) throws SQLException, IOException {
        if (LoginQuery.findUser(usernameField.getText(), passwordField.getText())){
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/home.fxml")));
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

            Scene home = new Scene(root,800, 600);
            stage.setTitle("");
            stage.setScene(home);
            stage.show();
        } else {
            //----------------Not Found Error--------------------//
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("");
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UTILITY);

            if (Locale.getDefault().getLanguage().equals("fr")){
                String filename = "utilities/lang";
                ResourceBundle rb = ResourceBundle.getBundle(filename, Locale.getDefault());
                alert.setContentText(rb.getString("loginError"));
            } else {
                alert.setContentText("Sorry, Username/Password combination not found.");
            }
            alert.showAndWait();
        }
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
