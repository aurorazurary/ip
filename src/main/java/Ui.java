import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles interactions with the user.
 */
public class Ui {
    private static final String GREETINGS = "Good to see you!\nI'm Overflow, lemme know what I could do for you :>";
    private static final String FAREWELL = "Looking for the next time we meet!";
    private Scanner scanner;

    /**
     * Creates a Ui object and initializes the scanner.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the welcome message.
     */
    public void showWelcome() {
        System.out.println(GREETINGS);
    }

    /**
     * Displays the goodbye message.
     */
    public void showGoodbye() {
        System.out.println(FAREWELL);
    }

    /**
     * Reads a command from the user.
     *
     * @return The user's input command.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays a message when a task is added.
     *
     * @param task The task that was added.
     * @param taskCount The total number of tasks.
     */
    public void showTaskAdded(Task task, int taskCount) {
        System.out.println("Got it! I've added the task: " + task);
        System.out.println("Currently you have " + taskCount + " tasks.");
    }

    /**
     * Displays a message when a task is deleted.
     *
     * @param task The task that was deleted.
     * @param taskCount The total number of tasks.
     */
    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println("Deleted! \n" + task);
        System.out.println("Currently you have " + taskCount + " tasks.");
    }

    /**
     * Displays a message when a task is marked.
     *
     * @param task The task that was marked.
     */
    public void showTaskMarked(Task task) {
        System.out.println("Marked! \n" + task);
    }

    /**
     * Displays a message when a task is unmarked.
     *
     * @param task The task that was unmarked.
     */
    public void showTaskUnmarked(Task task) {
        System.out.println("Unmarked! \n" + task);
    }

    /**
     * Displays all tasks in the task list.
     *
     * @param tasks The list of tasks to display.
     */
    public void showTasks(TaskList tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
        if (tasks.size() == 0) {
            System.out.println("There currently isn't any added task QAQ");
        }
    }

    /**
     * Displays the search results.
     *
     * @param results The list of tasks that match the search.
     */
    public void showSearchResults(ArrayList<Task> results) {
        if (results.isEmpty()) {
            System.out.println("No matching tasks found!");
        } else {
            System.out.println("Here are the matching tasks in your list:");
            for (int i = 0; i < results.size(); i++) {
                System.out.println((i + 1) + ". " + results.get(i));
            }
        }
    }

    /**
     * Displays an error message.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        System.out.println(message);
    }

    /**
     * Displays a loading error message.
     */
    public void showLoadingError() {
        System.out.println("No saved data found. Starting fresh!");
    }
}