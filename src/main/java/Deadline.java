/**
 * Represents a task that needs to be done before a specific deadline.
 */
public class Deadline extends Task {
    private String deadline;

    /**
     * Creates a new deadline task.
     *
     * @param name Description of the deadline task.
     * @param deadline The deadline by which the task should be completed.
     */
    public Deadline(String name, String deadline) {
        super(name);
        this.deadline = deadline;
    }

    @Override
    public String toFileFormat() {
        return "D | " + super.toFileFormat()
                + " | " + deadline;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + deadline + ")";
    }
}