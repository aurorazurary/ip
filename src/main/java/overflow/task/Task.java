package overflow.task;

/**
 * Represents a overflow.task with a description and completion status.
 */
public class Task {
    private String name;
    private boolean done = false;

    /**
     * Creates a new overflow.task with the given content.
     *
     * @param name Description of the overflow.task.
     */
    public Task(String name) {
        this.name = name;
    }

    /**
     * Marks the overflow.task as done.
     */
    public void mark() {
        done = true;
    }

    /**
     * Marks the overflow.task as not done.
     */
    public void unmark() {
        done = false;
    }

    /**
     * Return the name of the overflow.task.
     * @return name Name of the overflow.task.
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
        int isDone = done ? 1 : 0;
        return isDone + " | " + name;
    }

    @Override
    public String toString() {
        String mark = done ? "X" : " ";
        return "[" + mark + "] " + name;
    }
}
