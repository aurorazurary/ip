public class Event extends Task{
    private String start;
    private String end;

    public Event(String start, String end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        " (from: " + start +
        return "[D]" + super.toString() +
                " to: " + end +
                ")";
    }
}
