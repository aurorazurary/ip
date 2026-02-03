import java.util.ArrayList;

/**
 * Represents a list of tasks with operations to add, delete, mark, and unmark tasks.
 */
public class TaskList {
    private ArrayList<Task> tasks;

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
     * Adds a task to the list.
     *
     * @param task The task to add.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes a task from the list.
     *
     * @param index The index of the task to delete (0-based).
     * @return The deleted task.
     */
    public Task delete(int index) {
        return tasks.remove(index);
    }

    /**
     * Gets a task from the list.
     *
     * @param index The index of the task (0-based).
     * @return The task at the specified index.
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Marks a task as done.
     *
     * @param index The index of the task to mark (0-based).
     */
    public void mark(int index) {
        tasks.get(index).mark();
    }

    /**
     * Marks a task as not done.
     *
     * @param index The index of the task to unmark (0-based).
     */
    public void unmark(int index) {
        tasks.get(index).unmark();
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
}