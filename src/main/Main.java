package main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/** Starting point for the application. Contains main method. */
public class Main extends Application {
    /** Start method. Called by default by launching the application, starts the views the user sees. */
    @Override
    public void start(Stage stage) throws IOException {
        Parent window = FXMLLoader.load(getClass().getResource("/view/main_window.fxml"));

        stage.setTitle("Main");
        stage.setScene(new Scene(window, 800, 600));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
