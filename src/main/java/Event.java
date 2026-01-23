public class Event extends Task{
    private String start;
    private String end;

    public Event(String content, String start, String end) {
        super(content);
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {

        return "[E]" + super.toString() +
                " (from: " + start +
                " to: " + end +
                ")";
    }
}
