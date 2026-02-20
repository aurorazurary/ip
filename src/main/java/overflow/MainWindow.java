import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import overflow.Overflow;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    private static final String GREETINGS = "Good to see you!\nI'm Overflow, lemme know what I could do for you :>";

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Overflow overflow;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.jpg"));
    private Image overflowImage = new Image(this.getClass().getResourceAsStream("/images/overflow.jpg"));

    /**
     * Initializes the controller after FXML loading.
     * Sets up auto-scrolling and displays the greeting message.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().add(DialogBox.getOverflowDialog(GREETINGS, overflowImage));
    }

    /**
     * Injects the Overflow instance.
     *
     * @param overflow The Overflow chatbot instance.
     */
    public void setOverflow(Overflow overflow) {
        this.overflow = overflow;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Overflow's reply.
     * Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input.isEmpty()) {
            return;
        }

        dialogContainer.getChildren().add(DialogBox.getUserDialog(input, userImage));

        if (input.equals("bye")) {
            String farewell = "Looking for the next time we meet!";
            dialogContainer.getChildren().add(DialogBox.getOverflowDialog(farewell, overflowImage));
            userInput.setDisable(true);
            sendButton.setDisable(true);

            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(event -> Platform.exit());
            delay.play();
        } else {
            String response = overflow.getResponse(input);
            dialogContainer.getChildren().add(DialogBox.getOverflowDialog(response, overflowImage));
        }

        userInput.clear();
    }
}
