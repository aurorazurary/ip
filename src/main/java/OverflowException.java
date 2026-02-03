/**
 * Represents exceptions specific to the Overflow chatbot.
 */
public class OverflowException extends Exception {

    /**
     * Creates an OverflowException with the specified message.
     *
     * @param message The error message.
     */
    public OverflowException(String message) {
        super(message);
    }
}