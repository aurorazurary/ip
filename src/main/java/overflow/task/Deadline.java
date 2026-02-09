package overflow.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a overflow.task that needs to be done before a specific deadline.
 */
public class Deadline extends Task {
    private LocalDateTime deadline;

    /**
     * Creates a new deadline overflow.task.
     *
     * @param name Description of the deadline overflow.task.
     * @param deadline The deadline by which the overflow.task should be completed.
     */
    public Deadline(String name, LocalDateTime deadline) {
        super(name);
        this.deadline = deadline;
    }

    /**
     * Returns the deadline as a LocalDateTime.
     *
     * @return The deadline.
     */
    public LocalDateTime getDeadline() {
        return deadline;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: "
                + deadline.format(DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma")) + ")";
    }

    @Override
    public String toFileFormat() {
        String status = super.toString().contains("X") ? "1" : "0";
        return "D | " + status + " | " + getName() + " | "
                + deadline.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
    }
}