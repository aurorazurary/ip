import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import overflow.Overflow;

/**
 * Main GUI class for Overflow chatbot.
 */
public class Main extends Application {
    private static final String DEFAULT_FILE_PATH = "./data/tasks.txt";
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private ScrollPane scrollPane;
    private Overflow overflow;
    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.jpg"));
    private Image botImage = new Image(this.getClass().getResourceAsStream("/images/overflow.jpg"));

    @Override
    public void start(Stage stage) {
        // Initialize Overflow
        overflow = new Overflow(DEFAULT_FILE_PATH);

        // Create components
        scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        scrollPane.setContent(dialogContainer);

        userInput = new TextField();
        userInput.setOnAction(e -> handleUserInput());  // Allow Enter key to send
        sendButton = new Button("Send");

        // Layout
        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

        Scene scene = new Scene(mainLayout);

        // Formatting the window to look as expected
        stage.setTitle("Overflow");
        stage.setResizable(false);
        stage.setMinHeight(600.0);
        stage.setMinWidth(400.0);

        mainLayout.setPrefSize(400.0, 600.0);

        scrollPane.setPrefSize(385, 535);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);

        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);

        userInput.setPrefWidth(325.0);
        sendButton.setPrefWidth(55.0);

        AnchorPane.setTopAnchor(scrollPane, 1.0);
        AnchorPane.setBottomAnchor(sendButton, 1.0);
        AnchorPane.setRightAnchor(sendButton, 1.0);
        AnchorPane.setLeftAnchor(userInput, 1.0);
        AnchorPane.setBottomAnchor(userInput, 1.0);

        sendButton.setOnAction(e -> handleUserInput());

        stage.setScene(scene);
        stage.show();
    }

    private void handleUserInput() {
        String input = userInput.getText();
        if (!input.isEmpty()) {
            // Add user message
            dialogContainer.getChildren().add(new DialogBox(input, userImage));

            // Get bot response
            String response = overflow.getResponse(input);
            dialogContainer.getChildren().add(new DialogBox(response, botImage));

            userInput.clear();
        }
    }
}