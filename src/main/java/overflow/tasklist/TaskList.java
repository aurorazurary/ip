package overflow.tasklist;

import overflow.parser.exception.OverflowException;
import overflow.task.Task;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a list of tasks with operations to add, delete, mark, and unmark tasks.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Creates an empty overflow.tasklist.TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a overflow.tasklist.TaskList with the given list of tasks.
     *
     * @param tasks The initial list of tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a overflow.task to the list.
     *
     * @param task The overflow.task to add.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes a overflow.task from the list.
     *
     * @param index The index of the overflow.task to delete (0-based).
     * @return The deleted overflow.task.
     */
    public Task delete(int index) throws OverflowException {
        validateIndex(index);
        return tasks.remove(index);
    }

    /**
     * Gets a overflow.task from the list.
     *
     * @param index The index of the overflow.task (0-based).
     * @return The overflow.task at the specified index.
     */
    public Task get(int index) throws OverflowException {
        validateIndex(index);
        return tasks.get(index);
    }

    /**
     * Marks a overflow.task as done.
     *
     * @param index The index of the overflow.task to mark (0-based).
     */
    public void mark(int index) throws OverflowException {
        validateIndex(index);
        tasks.get(index).mark();
    }

    /**
     * Marks a overflow.task as not done.
     *
     * @param index The index of the overflow.task to unmark (0-based).
     */
    public void unmark(int index) throws OverflowException {
        validateIndex(index);
        tasks.get(index).unmark();
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The size of the overflow.task list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the internal ArrayList of tasks.
     *
     * @return The ArrayList of tasks.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    private void validateIndex(int index) throws OverflowException {
        if (index < 0 || index >= tasks.size()) {
            throw new OverflowException("OOPS! Task number is out of range!");
        }
    }

    /**
     * Finds tasks that contain any of the given keywords and groups them by keyword.
     *
     * @param keywords Array of keywords to search for.
     * @return HashMap mapping each keyword to its matching tasks.
     */
    public HashMap<String, ArrayList<Task>> find(String[] keywords) {
        HashMap<String, ArrayList<Task>> resultsByKeyword = new HashMap<>();

        for (String keyword : keywords) {
            ArrayList<Task> matchingTasks = new ArrayList<>();

            for (Task task : tasks) {
                String taskName = task.getName().toLowerCase();
                if (taskName.contains(keyword.toLowerCase())) {
                    matchingTasks.add(task);
                }
            }

            if (!matchingTasks.isEmpty()) {
                resultsByKeyword.put(keyword, matchingTasks);
            }
        }

        return resultsByKeyword;
    }
}