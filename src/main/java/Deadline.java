public class Deadline extends Task{
    private String ddl;

    public Deadline(String content, String ddl) {
        super(content);
        this.ddl = ddl;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + ddl + ")";
    }
}
