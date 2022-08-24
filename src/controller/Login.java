package controller;

import javafx.collections.ObservableList;
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
import model.Appointment;
import utilities.AppointmentDAO;
import utilities.CurrentSession;
import utilities.UserDAO;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.io.*;

/** Login Controller. */
public class Login implements Initializable {
    /** Username field. */
    public TextField usernameField;
    /** Password field. */
    public TextField passwordField;
    /** Login button. */
    public Button loginButton;
    /** Username Label. */
    public Label usernameLabel;
    /** Password label. */
    public Label passwordLabel;
    /** Timezone label. */
    public Label tzLabel;
    /** Language label. */
    public Label langLabel;
    /** Timezone text content. */
    public Label tzText;
    /** Language text content. */
    public Label langText;
    /** User object for the current session user. */
    public static model.User currUser;
    /** Gets the user object for the current session user.
     * @return a user the current session user. */
    public static model.User getUser(){
        return currUser;
    }

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

        tzText.setText(CurrentSession.getZone().toString());
        langText.setText(CurrentSession.getLangString());
    }

    /** Login button pressed, gather username and password and validate against DB.
     * Loads the main page if successfull, displays error if not.
     * <br>
     * Also writes to the login_activity file. Additionally, runs the appointment check function for
     * feedback on possible upcoming appointments for current user. */
    public void onLogin(ActionEvent actionEvent) throws SQLException, IOException {
        FileWriter fw = new FileWriter("login_activity.txt", true);
        PrintWriter pw = new PrintWriter(fw);

        if (UserDAO.validateUser(usernameField.getText(), passwordField.getText())){
            Integer id = UserDAO.getUserId(usernameField.getText(), passwordField.getText());
            currUser = UserDAO.getUser(id);

            pw.println("Attempt with username: " + usernameField.getText() + " at " + LocalDateTime.now() + " Status: SUCCESS");
            pw.close();

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/home.fxml")));
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

            Scene home = new Scene(root,800, 600);
            stage.setTitle("");
            stage.setScene(home);
            stage.show();

            apptLoginCheck();
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
            pw.println("Attempt with username: " + usernameField.getText() + " at " + LocalDateTime.now() + " Status: FAIL");
            pw.close();
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

    //-------------------------------------------------------------------FUNCTION DECLARATIONS--------------------------------------------------
    /** Checks for appointments within 15 min (plus or minus) of current system time. If there are appointments they are
     * displayed in an alert. If there aren't an alert indicating there are none is displayed. */
    public void apptLoginCheck(){
        ObservableList<Appointment> appointments = null;
        try {
            appointments = AppointmentDAO.getAllAppointments();
        } catch (Exception e) {
            //FIX**
        }
        Integer numAlerts = 0;
        for (Appointment appointment: appointments) {
            if (appointment.getStart().isAfter(LocalDateTime.now()) && appointment.getStart().isBefore(LocalDateTime.now().plusMinutes(15)) && appointment.getUser() == Login.getUser().getId()){
                //----------------Alert for appointments--------------------//
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Upcoming Appointment");
                alert.setHeaderText(null);
                alert.initStyle(StageStyle.UTILITY);
                alert.setContentText("Appointment ID: " + String.valueOf(appointment.getId()) + "\n" + "Start: " + appointment.getStartFormat());

                alert.showAndWait();
                numAlerts ++;
            }
        }

        if (numAlerts == 0){
            //----------------Alert for appointments--------------------//
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Upcoming Appointment");
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UTILITY);
            alert.setContentText("User: " + Login.getUser() + " has no upcoming appointments.");
            alert.showAndWait();
        }
    }
}
