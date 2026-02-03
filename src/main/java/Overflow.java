import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Represents the main chatbot that helps users manage their tasks.
 */
public class Overflow {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Creates an Overflow chatbot with the specified file path.
     *
     * @param filePath Path to the file where tasks are stored.
     */
    public Overflow(String filePath) {
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
     * Handles a user command.
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
            default:
                ui.showError("Sorry I don't understand what you are saying ;-;");
        }
    }

    /**
     * Handles the mark command.
     *
     * @param input The user's input.
     * @throws OverflowException If the index is invalid.
     * @throws IOException If there's an error saving tasks.
     */
    private void handleMark(String input) throws OverflowException, IOException {
        int index = Parser.parseIndex(input, 4) - 1;

        if (index < 0 || index >= tasks.size()) {
            throw new OverflowException("OOPS! Task number is out of range!");
        }

        tasks.mark(index);
        ui.showTaskMarked(tasks.get(index));
        storage.saveChange(tasks.getTasks());
    }

    /**
     * Handles the unmark command.
     *
     * @param input The user's input.
     * @throws OverflowException If the index is invalid.
     * @throws IOException If there's an error saving tasks.
     */
    private void handleUnmark(String input) throws OverflowException, IOException {
        int index = Parser.parseIndex(input, 6) - 1;

        if (index < 0 || index >= tasks.size()) {
            throw new OverflowException("OOPS! Task number is out of range!");
        }

        tasks.unmark(index);
        ui.showTaskUnmarked(tasks.get(index));
        storage.saveChange(tasks.getTasks());
    }

    /**
     * Handles the delete command.
     *
     * @param input The user's input.
     * @throws OverflowException If the index is invalid.
     * @throws IOException If there's an error saving tasks.
     */
    private void handleDelete(String input) throws OverflowException, IOException {
        int index = Parser.parseIndex(input, 6) - 1;

        if (index < 0 || index >= tasks.size()) {
            throw new OverflowException("OOPS! Task number is out of range!");
        }

        Task deletedTask = tasks.delete(index);
        ui.showTaskDeleted(deletedTask, tasks.size());
        storage.saveChange(tasks.getTasks());
    }

    /**
     * Handles the todo command.
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
     * Handles the deadline command.
     *
     * @param input The user's input.
     * @throws OverflowException If the deadline format is invalid.
     * @throws IOException If there's an error saving tasks.
     */
    private void handleDeadline(String input) throws OverflowException, IOException {
        String[] parts = Parser.parseDeadline(input);
        Task newTask = new Deadline(parts[0], parts[1]);
        tasks.add(newTask);
        ui.showTaskAdded(newTask, tasks.size());
        storage.saveChange(tasks.getTasks());
    }

    /**
     * Handles the event command.
     *
     * @param input The user's input.
     * @throws OverflowException If the event format is invalid.
     * @throws IOException If there's an error saving tasks.
     */
    private void handleEvent(String input) throws OverflowException, IOException {
        String[] parts = Parser.parseEvent(input);
        Task newTask = new Event(parts[0], parts[1], parts[2]);
        tasks.add(newTask);
        ui.showTaskAdded(newTask, tasks.size());
        storage.saveChange(tasks.getTasks());
    }

    /**
     * Main method to run the Overflow chatbot.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        new Overflow("./src/main/data/tasks.txt").run();
    }
}