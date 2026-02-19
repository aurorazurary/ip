package overflow;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import overflow.exception.OverflowException;
import overflow.parser.Parser;
import overflow.storage.Storage;
import overflow.task.Deadline;
import overflow.task.Event;
import overflow.task.Task;
import overflow.task.Todo;
import overflow.tasklist.TaskList;
import overflow.ui.Ui;
/**
 * Represents the main chatbot that helps users manage their tasks.
 */
public class Overflow {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Creates a chatbot.Overflow chatbot with the specified file path.
     *
     * @param filePath Path to the file where tasks are stored.
     */
    public Overflow(String filePath) {
        assert filePath != null : "File path cannot be null";
        assert !filePath.isEmpty() : "File path cannot be empty";

        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.loadTasks());
        } catch (FileNotFoundException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Runs the chatbot.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            try {
                String input = ui.readCommand();
                if (input.equals("bye")) {
                    isExit = true;
                    continue;
                }

                handleCommand(input);

            } catch (OverflowException e) {
                ui.showError(e.getMessage());
            } catch (IOException e) {
                ui.showError("Error saving tasks: " + e.getMessage());
            }
        }

        ui.showGoodbye();
    }

    /**
     * Handles a user input depending on the command word.
     *
     * @param input The user's input command.
     * @throws OverflowException If the command is invalid.
     * @throws IOException If there's an error saving tasks.
     */
    private void handleCommand(String input) throws OverflowException, IOException {
        String command = Parser.parseCommand(input);

        switch (command) {
        case "list":
            ui.showTasks(tasks);
            break;
        case "mark":
            handleMark(input);
            break;
        case "unmark":
            handleUnmark(input);
            break;
        case "delete":
            handleDelete(input);
            break;
        case "todo":
            handleTodo(input);
            break;
        case "deadline":
            handleDeadline(input);
            break;
        case "event":
            handleEvent(input);
            break;
        case "find":
            handleFind(input);
            break;
        default:
            ui.showError("Sorry I don't understand what you are saying ;-;");
        }
    }

    /**
     * Handles the mark command to mark a task.
     *
     * @param input The user's input.
     * @throws OverflowException If the index is invalid.
     * @throws IOException If there's an error saving tasks.
     */
    private void handleMark(String input) throws OverflowException, IOException {
        int index = Parser.parseIndex(input, 4) - 1;

        tasks.mark(index);
        ui.showTaskMarked(tasks.get(index));
        storage.saveChange(tasks.getTasks());
    }

    /**
     * Handles the unmark command to unmark a task.
     *
     * @param input The user's input.
     * @throws OverflowException If the index is invalid.
     * @throws IOException If there's an error saving tasks.
     */
    private void handleUnmark(String input) throws OverflowException, IOException {
        int index = Parser.parseIndex(input, 6) - 1;

        tasks.unmark(index);
        ui.showTaskUnmarked(tasks.get(index));
        storage.saveChange(tasks.getTasks());
    }

    /**
     * Handles the delete command to delete a task.
     *
     * @param input The user's input.
     * @throws OverflowException If the index is invalid.
     * @throws IOException If there's an error saving tasks.
     */
    private void handleDelete(String input) throws OverflowException, IOException {
        int index = Parser.parseIndex(input, 6) - 1;

        Task deletedTask = tasks.delete(index);
        ui.showTaskDeleted(deletedTask, tasks.size());
        storage.saveChange(tasks.getTasks());
    }

    /**
     * Handles the todo command to create a todo task.
     *
     * @param input The user's input.
     * @throws OverflowException If the description is empty.
     * @throws IOException If there's an error saving tasks.
     */
    private void handleTodo(String input) throws OverflowException, IOException {
        String description = Parser.parseTodo(input);
        Task newTask = new Todo(description);
        tasks.add(newTask);
        ui.showTaskAdded(newTask, tasks.size());
        storage.saveChange(tasks.getTasks());
    }

    /**
     * Handles the deadline command to create a deadline task.
     *
     * @param input The user's input.
     * @throws OverflowException If the deadline format is invalid.
     * @throws IOException If there's an error saving tasks.
     */
    private void handleDeadline(String input) throws OverflowException, IOException {
        Object[] parts = Parser.parseDeadline(input);
        String description = (String) parts[0];
        LocalDateTime deadline = (LocalDateTime) parts[1];
        Task newTask = new Deadline(description, deadline);
        tasks.add(newTask);
        ui.showTaskAdded(newTask, tasks.size());
        storage.saveChange(tasks.getTasks());
    }

    /**
     * Handles the event command to create an event task.
     *
     * @param input The user's input.
     * @throws OverflowException If the event format is invalid.
     * @throws IOException If there's an error saving tasks.
     */
    private void handleEvent(String input) throws OverflowException, IOException {
        Object[] parts = Parser.parseEvent(input);
        String description = (String) parts[0];
        LocalDateTime startTime = (LocalDateTime) parts[1];
        LocalDateTime endTime = (LocalDateTime) parts[2];
        Task newTask = new Event(description, startTime, endTime);
        tasks.add(newTask);
        ui.showTaskAdded(newTask, tasks.size());
        storage.saveChange(tasks.getTasks());
    }

    /**
     * Handles the find command to find tasks including any give keywords.
     *
     * @param input The user's input.
     * @throws OverflowException If no keywords provided.
     */
    private void handleFind(String input) throws OverflowException {
        String[] keywords = Parser.parseKeyword(input, 4);
        HashMap<String, ArrayList<Task>> results = tasks.find(keywords);
        ui.showSearchResults(results);
    }

    /**
     * Processes user input and returns the response.
     *
     * @param input The user's input command.
     * @return The chatbot's response as a String.
     */
    public String getResponse(String input) {
        if (input.equals("bye")) {
            return "Looking for the next time we meet!";
        }

        try {
            // Capture the output by temporarily redirecting it
            java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
            java.io.PrintStream ps = new java.io.PrintStream(baos);
            java.io.PrintStream old = System.out;
            System.setOut(ps);

            handleCommand(input);

            System.out.flush();
            System.setOut(old);

            return baos.toString().trim();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    /**
     * Main method to run the chatbot.Overflow chatbot.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        new Overflow("./data/tasks.txt").run();
    }
}
