import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import overflow.Overflow;

/**
 * A GUI for Overflow using FXML.
 */
public class Main extends Application {
    private static final String DEFAULT_FILE_PATH = "./data/tasks.txt";
    private static final String MAINWINDOW_FXML_FILE_PATH = "/view/MainWindow.fxml";

    private Overflow overflow = new Overflow(DEFAULT_FILE_PATH);

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(MAINWINDOW_FXML_FILE_PATH));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);

            stage.setScene(scene);
            stage.setTitle("Overflow");

            fxmlLoader.<MainWindow>getController().setOverflow(overflow);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
