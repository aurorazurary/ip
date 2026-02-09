package overflow.task;

/**
 * Represents a todo overflow.task without any date/time attached.
 */
public class Todo extends Task {

    /**
     * Creates a new todo overflow.task.
     *
     * @param name Description of the todo.
     */
    public Todo(String name) {
        super(name);
    }

    @Override
    public String toFileFormat() {
        return "T | " + super.toFileFormat();
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}