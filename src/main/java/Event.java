/**
 * Represents a task that starts at a specific time and ends at a specific time.
 */
public class Event extends Task {
    private String startTime;
    private String endTime;

    /**
     * Creates a new event task.
     *
     * @param name Description of the event.
     * @param startTime Start time of the event.
     * @param endTime End time of the event.
     */
    public Event(String name, String startTime, String endTime) {
        super(name);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toFileFormat() {
        return "E | " + super.toFileFormat()
                + " | " + startTime + " | " + endTime;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + startTime
                + " to: " + endTime
                + ")";
    }
}