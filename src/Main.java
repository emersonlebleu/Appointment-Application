import data.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/** Starting point for the application. Contains main method. */
public class Main extends Application {
    /** Start method. Called by default by launching the application, starts the views the user sees.
     * -------------ERROR CORRECTED: Mistakenly had not put a closing bracket at the end of the start method. This caused
     * the application not to compile. The error appeared on the main(String[] args) however. It took me a
     * while to dig in and find where the actual error was. Eventually, I noticed the error wasn't on just one part
     * of that line so assumed the error had to be further upp in the code before that line and I found the missing
     * closing bracket. */
    @Override
    public void start(Stage stage) throws IOException {
        Parent window = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/login.fxml")));

        stage.setTitle("Login");
        Scene mainScene = new Scene(window, 400, 280);
        stage.setScene(mainScene);
        stage.setResizable(false);

        stage.show();
    }

    public static void main(String[] args) {
        JDBC.connect();
        launch();
        JDBC.disconnect();
    }
}
