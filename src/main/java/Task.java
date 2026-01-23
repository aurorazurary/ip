public class Task {
    private String content;
    private boolean done = false;

    public Task(String content) {
        this.content = content;
    }

    public void mark() {
        done = true;
    }

    public void unmark() {
        done = false;
    }

    @Override
    public String toString() {
        String mark = done ? "✓" : " ";
        return "[" +
                mark +
                "] " +
                content;
    }
}
