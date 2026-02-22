package overflow.tasklist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import overflow.exception.OverflowException;
import overflow.task.Task;

/**
 * Represents a list of tasks with operations to add, delete, mark, and unmark tasks.
 */
public class TaskList {
    private ArrayList<Task> tasks;
    private String lastAction = null;
    private Task lastTask = null;
    private int lastIndex;

    /**
     * Creates an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a TaskList with the given list of tasks.
     *
     * @param tasks The initial list of tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Restores the task list to the previously saved state.
     *
     * @throws OverflowException If there is no previous state to restore.
     */
    public void undo() throws OverflowException {
        if (lastAction == null) {
            throw new OverflowException("Nothing to undo!");
        }

        switch (lastAction) {
        case "add":
            tasks.remove(lastTask);  // Remove the task that was added
            break;
        case "delete":
            tasks.add(lastIndex, lastTask);  // Re-add at original position
            break;
        case "mark":
            tasks.get(lastIndex).unmark();  // Unmark it
            break;
        case "unmark":
            tasks.get(lastIndex).mark();  // Mark it back
            break;
        }

        lastAction = null;
    }

    /**
     * Adds a task to the list.
     *
     * @param task The task to add.
     */
    public void add(Task task) {
        tasks.add(task);
        lastAction = "add";
        lastTask = task;
    }

    /**
     * Deletes a task from the list.
     *
     * @param index The index of the task to delete (0-based).
     * @return The deleted task.
     */
    public Task delete(int index) throws OverflowException {
        validateIndex(index);
        Task deleted = tasks.remove(index);
        lastAction = "delete";
        lastTask = deleted;
        lastIndex = index;
        return deleted;
    }

    /**
     * Gets a task from the list.
     *
     * @param index The index of the task (0-based).
     * @return The task at the specified index.
     */
    public Task get(int index) throws OverflowException {
        validateIndex(index);
        assert index >= 0 : "Index should be non-negative after validation";
        assert index < tasks.size() : "Index should be within bounds after validation";
        return tasks.get(index);
    }

    /**
     * Marks a task as done.
     *
     * @param index The index of the task to mark (0-based).
     */
    public void mark(int index) throws OverflowException {
        validateIndex(index);
        tasks.get(index).mark();
        lastAction = "mark";
        lastIndex = index;
    }

    /**
     * Marks a task as not done.
     *
     * @param index The index of the task to unmark (0-based).
     */
    public void unmark(int index) throws OverflowException {
        validateIndex(index);
        tasks.get(index).unmark();
        lastAction = "unmark";
        lastIndex = index;
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The size of the task list.
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
            ArrayList<Task> matchingTasks = tasks.stream()
                    .filter(task -> task.getName().toLowerCase().contains(keyword.toLowerCase()))
                    .collect(Collectors.toCollection(ArrayList::new));

            if (!matchingTasks.isEmpty()) {
                resultsByKeyword.put(keyword, matchingTasks);
            }
        }

        return resultsByKeyword;
    }
}
