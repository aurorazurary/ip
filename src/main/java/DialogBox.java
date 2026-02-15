import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.Node;

/**
 * Represents a dialog box consisting of an ImageView and a Label.
 */
public class DialogBox extends HBox {
    private Label text;
    private ImageView displayPicture;

    /**
     * Creates a DialogBox with the specified text and image.
     *
     * @param s The text to display.
     * @param i The image to display.
     */
    private DialogBox(String s, Image i) {
        text = new Label(s);
        displayPicture = new ImageView(i);

        // Styling the dialog box
        text.setWrapText(true);
        displayPicture.setFitWidth(80.0);
        displayPicture.setFitHeight(80.0);
        this.setAlignment(Pos.TOP_RIGHT);

        this.getChildren().addAll(text, displayPicture);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        this.setAlignment(Pos.TOP_LEFT);
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        FXCollections.reverse(tmp);
        this.getChildren().setAll(tmp);
    }

    /**
     * Creates a dialog box for user messages.
     *
     * @param text The user's message.
     * @param image The user's avatar image.
     * @return A DialogBox with user styling.
     */
    public static DialogBox getUserDialog(String text, Image image) {
        return new DialogBox(text, image);
    }

    /**
     * Creates a dialog box for bot messages.
     *
     * @param text The bot's message.
     * @param image The bot's avatar image.
     * @return A DialogBox with bot styling (flipped).
     */
    public static DialogBox getOverflowDialog(String text, Image image) {
        DialogBox dialogBox = new DialogBox(text, image);
        dialogBox.flip();
        return dialogBox;
    }
}
