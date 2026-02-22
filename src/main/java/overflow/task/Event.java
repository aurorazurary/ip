package overflow.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that starts at a specific time and ends at a specific time.
 */
public class Event extends Task {
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    /**
     * Creates a new event task.
     *
     * @param name Description of the event.
     * @param startTime Start time of the event.
     * @param endTime End time of the event.
     */
    public Event(String name, LocalDateTime startTime, LocalDateTime endTime) {
        super(name);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Returns the start time of the event.
     *
     * @return The start time.
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * Returns the end time of the event.
     *
     * @return The end time.
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    @Override
    public String toFileFormat() {
        String status = super.toString().contains("X") ? "1" : "0";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        return "E | " + status + " | " + getName() + " | "
                + startTime.format(formatter) + " | " + endTime.format(formatter);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");
        return "[E]" + super.toString()
                + " (from: " + startTime.format(formatter)
                + " to: " + endTime.format(formatter)
                + ")";
    }
}
