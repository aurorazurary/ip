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
    private static final int TODO_COMMAND_LENGTH = 4; // length of "todo"
    private static final int DEADLINE_COMMAND_LENGTH = 8; // length of "deadline"
    private static final int EVENT_COMMAND_LENGTH = 5; // length of "event"

    /**
     * Parses the user input and returns the command type.
     *
     * @param input The user's input.
     * @return The command type (e.g., "list", "mark", "todo").
     */
    public static String parseCommand(String input) {
        String[] commandParts = input.split(" ", 2);
        String command = commandParts[0];

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
        case "undo":
            return "undo";
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
        String description = input.substring(TODO_COMMAND_LENGTH).trim();
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
        String processedInput = input.substring(DEADLINE_COMMAND_LENGTH).trim();

        if (processedInput.isEmpty()) {
            throw new OverflowException("OOPS!!! The description of a deadline cannot be empty.");
        }

        String[] deadlineParts = processedInput.split(" /by ");

        if (deadlineParts.length < 2 || deadlineParts[1].trim().isEmpty()) {
            throw new OverflowException("OOPS!!! The deadline must have a /by time.");
        }

        String description = deadlineParts[0];
        LocalDateTime dateTime = parseDateTime(deadlineParts[1]); // This will throw if format is wrong

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
        String processedInput = input.substring(EVENT_COMMAND_LENGTH).trim();

        if (processedInput.isEmpty()) {
            throw new OverflowException("OOPS!!! The description of an event cannot be empty.");
        }

        String[] eventParts = processedInput.split(" /from ");
        if (eventParts.length < 2) {
            throw new OverflowException("OOPS!!! The event must have a /from time.");
        }

        String description = eventParts[0];
        String[] eventTimeParts = eventParts[1].split(" /to ");

        if (eventTimeParts.length < 2 || eventTimeParts[1].trim().isEmpty()) {
            throw new OverflowException("OOPS!!! The event must have a /to time.");
        }

        LocalDateTime startTime = parseDateTime(eventTimeParts[0]);
        LocalDateTime endTime = parseDateTime(eventTimeParts[1]);

        return new Object[]{description, startTime, endTime};
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
            int index = Integer.parseInt(indexString);
            assert index > 0 : "Parsed index should be positive";
            return index;
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

    /**
     * Parses a date-time string into LocalDateTime, allows a variety of
     * input formats.
     *
     * @param dateTimeString The date-time string in various formats.
     * @return LocalDateTime object.
     * @throws OverflowException If the date-time format is invalid.
     */
    public static LocalDateTime parseDateTime(String dateTimeString) throws OverflowException {
        String dateString = dateTimeString.trim().toLowerCase();

        try {
            // now
            if (dateString.equals("now")) {
                return LocalDateTime.now();
            }

            // today
            if (dateString.equals("today")) {
                return LocalDate.now().atStartOfDay();
            }

            // HHmm
            if (dateString.matches("\\d{4}")) {
                LocalTime timeOfDay = LocalTime.parse(dateString, DateTimeFormatter.ofPattern("HHmm"));
                return LocalDate.now().atTime(timeOfDay);
            }

            // MM-dd HHmm
            if (dateString.matches("\\d{2}-\\d{2} \\d{4}")) {
                int year = LocalDate.now().getYear();
                DateTimeFormatter monthDayTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
                return LocalDateTime.parse(year + "-" + dateString, monthDayTimeFormatter);
            }

            // MM-dd
            if (dateString.matches("\\d{2}-\\d{2}")) {
                int year = LocalDate.now().getYear();
                return LocalDate.parse(year + "-" + dateString).atStartOfDay();
            }

            // yyyy-MM-dd HHmm
            if (dateString.matches("\\d{4}-\\d{2}-\\d{2} \\d{4}")) {
                DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
                return LocalDateTime.parse(dateString, fullDateTimeFormatter);
            }

            // yyyy-MM-dd
            return LocalDate.parse(dateString).atStartOfDay();

        } catch (DateTimeParseException e) {
            throw new OverflowException(
                    "OOPS! Use: now, today, HHmm, MM-dd, MM-dd HHmm, yyyy-MM-dd, or yyyy-MM-dd HHmm"
                            + " to indicate time!"
            );
        }
    }

}
