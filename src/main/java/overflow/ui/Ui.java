package overflow.ui;

import overflow.exception.OverflowException;
import overflow.task.Task;
import overflow.tasklist.TaskList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Handles interactions with the user.
 */
public class Ui {
    private static final String GREETINGS = "Good to see you!\nI'm chatbot.Overflow, lemme know what I could do for you :>";
    private static final String FAREWELL = "Looking for the next time we meet!";
    private Scanner scanner;

    /**
     * Creates a overflow.ui.Ui object and initializes the scanner.
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
     * Displays a message when a overflow.task is added.
     *
     * @param task The overflow.task that was added.
     * @param taskCount The total number of tasks.
     */
    public void showTaskAdded(Task task, int taskCount) {
        System.out.println("Got it! I've added the overflow.task: " + task);
        System.out.println("Currently you have " + taskCount + " tasks.");
    }

    /**
     * Displays a message when a overflow.task is deleted.
     *
     * @param task The overflow.task that was deleted.
     * @param taskCount The total number of tasks.
     */
    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println("Deleted! \n" + task);
        System.out.println("Currently you have " + taskCount + " tasks.");
    }

    /**
     * Displays a message when a overflow.task is marked.
     *
     * @param task The overflow.task that was marked.
     */
    public void showTaskMarked(Task task) {
        System.out.println("Marked! \n" + task);
    }

    /**
     * Displays a message when a overflow.task is unmarked.
     *
     * @param task The overflow.task that was unmarked.
     */
    public void showTaskUnmarked(Task task) {
        System.out.println("Unmarked! \n" + task);
    }

    /**
     * Displays all tasks in the overflow.task list.
     *
     * @param tasks The list of tasks to display.
     */
    public void showTasks(TaskList tasks) throws OverflowException {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
        if (tasks.size() == 0) {
            System.out.println("There currently isn't any added overflow.task QAQ");
        }
    }

    /**
     * Displays the search results grouped by keyword.
     *
     * @param resultsByKeyword HashMap mapping keywords to their matching tasks.
     */
    public void showSearchResults(HashMap<String, ArrayList<Task>> resultsByKeyword) {
        if (resultsByKeyword.isEmpty()) {
            System.out.println("No matching tasks found!");
            return;
        }

        for (String keyword : resultsByKeyword.keySet()) {
            ArrayList<Task> tasks = resultsByKeyword.get(keyword);
            System.out.println("\nTasks matching \"" + keyword + "\":");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
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