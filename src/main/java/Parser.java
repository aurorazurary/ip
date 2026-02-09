/**
 * Parses user input into commands and their parameters.
 */
public class Parser {

    /**
     * Parses the user input and returns the command type.
     *
     * @param input The user's input.
     * @return The command type (e.g., "list", "mark", "todo").
     */
    public static String parseCommand(String input) {
        String[] parts = input.split(" ", 2);
        String command = parts[0];

        switch (command) {
        case "list":
            return "list";
        case "mark":
            return "mark";
        case "unmark":
            return "unmark";
        case "delete":
            return "delete";
        case "todo":
            return "todo";
        case "deadline":
            return "deadline";
        case "event":
            return "event";
        case "find":
            return "find";
        default:
            return "unknown";
        }
    }

    /**
     * Parses a todo command.
     *
     * @param input The full user input.
     * @return The description of the todo.
     * @throws OverflowException If the description is empty.
     */
    public static String parseTodo(String input) throws OverflowException {
        String description = input.substring(4).trim();
        if (description.isEmpty()) {
            throw new OverflowException("OOPS!!! The description of a todo cannot be empty.");
        }
        return description;
    }

    /**
     * Parses a deadline command.
     *
     * @param input The full user input.
     * @return An array with [description, deadline].
     * @throws OverflowException If the description or deadline is invalid.
     */
    public static String[] parseDeadline(String input) throws OverflowException {
        String processedInput = input.substring(8).trim();

        if (processedInput.isEmpty()) {
            throw new OverflowException("OOPS!!! The description of a deadline cannot be empty.");
        }

        String[] parts = processedInput.split(" /by ");

        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new OverflowException("OOPS!!! The deadline must have a /by time.");
        }

        return new String[]{parts[0], parts[1]};
    }

    /**
     * Parses an event command.
     *
     * @param input The full user input.
     * @return An array with [description, startTime, endTime].
     * @throws OverflowException If the description or times are invalid.
     */
    public static String[] parseEvent(String input) throws OverflowException {
        String processedInput = input.substring(5).trim();

        if (processedInput.isEmpty()) {
            throw new OverflowException("OOPS!!! The description of an event cannot be empty.");
        }

        String[] parts = processedInput.split(" /from ");
        if (parts.length < 2) {
            throw new OverflowException("OOPS!!! The event must have a /from time.");
        }

        String description = parts[0];
        String[] timeParts = parts[1].split(" /to ");

        if (timeParts.length < 2 || timeParts[1].trim().isEmpty()) {
            throw new OverflowException("OOPS!!! The event must have a /to time.");
        }

        return new String[]{description, timeParts[0], timeParts[1]};
    }

    /**
     * Parses an index from mark/unmark/delete commands.
     *
     * @param input The full user input.
     * @param commandLength The length of the command word.
     * @return The task index (1-based).
     * @throws OverflowException If the index is missing or invalid.
     */
    public static int parseIndex(String input, int commandLength) throws OverflowException {
        String indexString = input.substring(commandLength).trim();

        if (indexString.isEmpty()) {
            throw new OverflowException("OOPS! You have to choose a task number!");
        }

        try {
            return Integer.parseInt(indexString);
        } catch (NumberFormatException e) {
            throw new OverflowException("OOPS! Please provide a valid task number!");
        }
    }

    /**
     * Parses keywords from a find command.
     *
     * @param input The full user input.
     * @param commandLength The length of the command word.
     * @return An array of keywords to search for.
     * @throws OverflowException If no keywords are provided.
     */
    public static String[] parseKeyword(String input, int commandLength) throws OverflowException {
        String keywordString = input.substring(commandLength).trim();

        if (keywordString.isEmpty()) {
            throw new OverflowException("OOPS! You have to key in some keywords for me to find!");
        }

        return keywordString.split("\\s+");
    }

}