import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a chatbot that helps users manage their tasks.
 * The chatbot can add, list, mark, unmark, and delete tasks.
 */
public class Overflow {
    private static final String GREETINGS = "Good to see you!\nI'm Overflow, lemme know what I could do for you :>";
    private static final String FAREWELL = "Looking for the next time we meet!";
    private Storage fileHandler;
    private ArrayList<Task> tasks;

    public Overflow() {
        fileHandler = new Storage("./src/main/data/tasks.txt");

        try {
            tasks = fileHandler.loadTasks();
        } catch (FileNotFoundException e) {
            tasks = new ArrayList<>();
        }
    }

    /**
     * Adds a new task based on the user's input.
     * Supports three types of tasks: todo, deadline, and event.
     *
     * @param input The user's input containing the task type and details.
     */
    public void addTask(String input) throws IOException {
        String processedInput = input;
        Task newTask = null;

        if (processedInput.startsWith("todo")) {
            processedInput = processedInput.substring(4).trim();

            if (processedInput.isEmpty()) {
                System.out.println(" OOPS!!! The description of a todo cannot be empty.");
                return;
            }

            newTask = new Todo(processedInput);
        } else if (processedInput.startsWith("deadline")) {
            processedInput = processedInput.substring(8).trim();

            if (processedInput.isEmpty()) {
                System.out.println(" OOPS!!! The description of a deadline cannot be empty.");
                return;
            }

            String[] parts = processedInput.split(" /by ");

            if (parts.length < 2 || parts[1].trim().isEmpty()) {
                System.out.println(" OOPS!!! The deadline must have a /by time.");
                return;
            }

            newTask = new Deadline(parts[0], parts[1]);
        } else if (processedInput.startsWith("event")) {
            processedInput = processedInput.substring(5).trim();
            if (processedInput.isEmpty()) {
                System.out.println(" OOPS!!! The description of an event cannot be empty.");
                return;
            }

            String[] parts = processedInput.split(" /from ");
            if (parts.length < 2) {
                System.out.println(" OOPS!!! The event must have a /from time.");
                return;
            }

            String description = parts[0];
            String[] timeParts = parts[1].split(" /to ");
            if (timeParts.length < 2 || timeParts[1].trim().isEmpty()) {
                System.out.println(" OOPS!!! The event must have a /to time.");
                return;
            }

            String startTime = timeParts[0];
            String endTime = timeParts[1];
            newTask = new Event(description, startTime, endTime);
        } else {
            System.out.println("Sorry I don't understand what you are saying ;-;");
            return;
        }

        tasks.add(newTask);
        System.out.println("Got it! I've added the task: " + newTask);
        System.out.println("Currently you have " + tasks.size() + " tasks.");
    }

    /**
     * Displays all tasks in the task list.
     */
    public void listTasks() {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    /**
     * Marks a task as done.
     *
     * @param index The index of the task to mark (1-based).
     */
    public void mark(String index) {
        if (index.isEmpty()) {
            System.out.println("OOPS! You have to choose a task number!");
            return;
        }

        int taskIndex = Integer.parseInt(index) - 1;
        tasks.get(taskIndex).mark();
        System.out.println("Marked! \n" + tasks.get(taskIndex));
    }

    /**
     * Marks a task as not done.
     *
     * @param index The index of the task to unmark (1-based).
     */
    public void unmark(String index) {
        if (index.isEmpty()) {
            System.out.println("OOPS! You have to choose a task number!");
            return;
        }

        int taskIndex = Integer.parseInt(index) - 1;
        tasks.get(taskIndex).unmark();
        System.out.println("Unmarked! \n" + tasks.get(taskIndex));
    }

    /**
     * Deletes a task from the task list.
     *
     * @param index The index of the task to delete (1-based).
     */
    public void delete(String index) {
        if (index.isEmpty()) {
            System.out.println("OOPS! You have to choose a task number!");
            return;
        }

        int taskIndex = Integer.parseInt(index) - 1;
        Task removedTask = tasks.get(taskIndex);
        tasks.remove(taskIndex);
        System.out.println("Deleted! \n" + removedTask);
        System.out.println("Currently you have " + tasks.size() + " tasks.");
    }

    /**
     * Processes user input and executes the corresponding command.
     *
     * @param input The user's input command.
     */
    public void handleInput(String input) throws IOException {
        if (input.equals("list")) {
            listTasks();
        } else if (input.startsWith("mark")) {
            this.mark(input.substring(4).trim());
            saveTasks();
        } else if (input.startsWith("unmark")) {
            this.unmark(input.substring(6).trim());
            saveTasks();
        } else if (input.startsWith("delete")) {
            this.delete(input.substring(6).trim());
            saveTasks();
        } else {
            this.addTask(input);
            saveTasks();
        }
    }

    private void saveTasks() {
        try {
            fileHandler.saveChange(tasks);
        } catch (IOException e) {
            System.out.println("Error saving: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
        Overflow bot = new Overflow();

        System.out.println(GREETINGS);
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        while (!input.equals("bye")) {
            bot.handleInput(input);
            input = scanner.nextLine();
        }
        System.out.println(FAREWELL);
    }
}
