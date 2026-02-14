import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Main GUI class for Overflow chatbot.
 */
public class Main extends Application {
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private ScrollPane scrollPane;

    @Override
    public void start(Stage stage) {
        // Create components
        scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        scrollPane.setContent(dialogContainer);

        userInput = new TextField();
        sendButton = new Button("Send");

        sendButton.setOnAction(e -> handleUserInput());

        // Layout
        VBox mainLayout = new VBox(10);
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

        Scene scene = new Scene(mainLayout, 400, 600);

        stage.setTitle("Overflow Chatbot");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Separates the user and the bot's inputs
     */
    private void handleUserInput() {
        String input = userInput.getText();
        if (!input.isEmpty()) {
            // Add user message to chat
            dialogContainer.getChildren().add(new javafx.scene.control.Label("You: " + input));

            // Add bot response
            dialogContainer.getChildren().add(new javafx.scene.control.Label("Overflow: Got it!"));

            userInput.clear();
        }
    }
}

