/**
 * Represents a task with a description and completion status.
 */
public class Task {
    private String name;
    private boolean done = false;

    /**
     * Creates a new task with the given content.
     *
     * @param name Description of the task.
     */
    public Task(String name) {
        this.name = name;
    }

    /**
     * Marks the task as done.
     */
    public void mark() {
        done = true;
    }

    /**
     * Marks the task as not done.
     */
    public void unmark() {
        done = false;
    }

    /**
     * Return the name of the task.
     * @return name Name of the task.
     */
    public String getName() {
        return name;
    }

    public String toFileFormat() {
        int isDone = done ? 1 : 0;
        return isDone + " | " + name;
    }
    @Override
    public String toString() {
        String mark = done ? "X" : " ";
        return "[" + mark + "] " + name;
    }
}