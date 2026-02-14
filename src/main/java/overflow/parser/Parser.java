package overflow.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import overflow.exception.OverflowException;

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
     * @return An array with [description, LocalDateTime].
     * @throws OverflowException If the description or deadline is invalid.
     */
    public static Object[] parseDeadline(String input) throws OverflowException {
        String processedInput = input.substring(8).trim();

        if (processedInput.isEmpty()) {
            throw new OverflowException("OOPS!!! The description of a deadline cannot be empty.");
        }

        String[] parts = processedInput.split(" /by ");

        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new OverflowException("OOPS!!! The deadline must have a /by time.");
        }

        String description = parts[0];
        LocalDateTime dateTime = parseDateTime(parts[1]); // This will throw if format is wrong

        return new Object[]{description, dateTime};
    }

    /**
     * Parses an event command.
     *
     * @param input The full user input.
     * @return An array with [description, startTime as LocalDateTime, endTime as LocalDateTime].
     * @throws OverflowException If the description or times are invalid.
     */
    public static Object[] parseEvent(String input) throws OverflowException {
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

        LocalDateTime startTime = parseDateTime(timeParts[0]);
        LocalDateTime endTime = parseDateTime(timeParts[1]);

        return new Object[]{description, startTime, endTime};
    }

    /**
     * Parses an index from mark/unmark/delete commands.
     *
     * @param input The full user input.
     * @param commandLength The length of the command word.
     * @return The overflow.task index (1-based).
     * @throws OverflowException If the index is missing or invalid.
     */
    public static int parseIndex(String input, int commandLength) throws OverflowException {
        String indexString = input.substring(commandLength).trim();

        if (indexString.isEmpty()) {
            throw new OverflowException("OOPS! You have to choose a overflow.task number!");
        }

        try {
            return Integer.parseInt(indexString);
        } catch (NumberFormatException e) {
            throw new OverflowException("OOPS! Please provide a valid overflow.task number!");
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

    /**
     * Parses a date-time string into LocalDateTime, allows a variety of
     * input formate.
     *
     * @param dateTimeString The date-time string in various formats.
     * @return LocalDateTime object.
     * @throws OverflowException If the date-time format is invalid.
     */
    public static LocalDateTime parseDateTime(String dateTimeString) throws OverflowException {
        String string = dateTimeString.trim().toLowerCase();

        try {
            //now
            if (string.equals("now")) {
                return LocalDateTime.now();
            }

            // today
            if (string.equals("today")) {
                return LocalDate.now().atStartOfDay();
            }

            // HHmm
            if (string.matches("\\d{4}")) {
                LocalTime exactTime = LocalTime.parse(string, DateTimeFormatter.ofPattern("HHmm"));
                return LocalDate.now().atTime(exactTime);
            }

            // MM-dd HHmm
            if (string.matches("\\d{2}-\\d{2} \\d{4}")) {
                int year = LocalDate.now().getYear();
                DateTimeFormatter exactTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
                return LocalDateTime.parse(year + "-" + string, exactTime);
            }

            // MM-dd
            if (string.matches("\\d{2}-\\d{2}")) {
                int year = LocalDate.now().getYear();
                return LocalDate.parse(year + "-" + string).atStartOfDay();
            }

            // yyyy-MM-dd HHmm
            if (string.matches("\\d{4}-\\d{2}-\\d{2} \\d{4}")) {
                DateTimeFormatter exactTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
                return LocalDateTime.parse(string, exactTime);
            }

            // yyyy-MM-dd
            return LocalDate.parse(string).atStartOfDay();

        } catch (DateTimeParseException e) {
            throw new OverflowException(
                    "OOPS! Use: now, today, HHmm, MM-dd, MM-dd HHmm, yyyy-MM-dd, or yyyy-MM-dd HHmm"
                            + " to indicate time!"
            );
        }
    }

}
