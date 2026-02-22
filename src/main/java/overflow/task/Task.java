package overflow.task;

/**
 * Represents a task with a description and completion status.
 */
public class Task {
    private String name;
    private boolean isDone = false;

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
        isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void unmark() {
        isDone = false;
    }

    /**
     * Return the name of the task.
     *
     * @return name Name of the task.
     */
    public String getName() {
        return name;
    }

    /**
     * Converts a Task object into file format for storage.
     *
     * @return A string representation in the format "status | name" where status is 1 for done, 0 for not done.
     */
    public String toFileFormat() {
        int isDone = this.isDone ? 1 : 0;
        return isDone + " | " + name;
    }

    @Override
    public String toString() {
        String mark = isDone ? "X" : " ";
        return "[" + mark + "] " + name;
    }
}
