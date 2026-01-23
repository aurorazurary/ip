public class Task {
    private String content;
    private boolean done = false;

    public Task(String content) {
        this.content = content;
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
