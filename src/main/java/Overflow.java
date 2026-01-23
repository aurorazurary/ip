import java.util.ArrayList;
import java.util.Scanner;

public class Overflow {
    static String GREETINGS = "Good to see you!\nI'm Overflow, lemme know what I could do for you :>";
    static String FAREWELL = "Looking for the next time we meet!";
    private ArrayList<Task> tasks = new ArrayList<>();
    private int current = -1;

    public void add(String input) {
        String process = input;
        Task newT = null;

        if (process.startsWith("todo")) {
            process = process.substring(4).trim();

            if (process.isEmpty()) {
                System.out.println(" OOPS!!! The description of a todo cannot be empty.");
                return;
            }

            newT = new Todo(process);
        } else if (process.startsWith("deadline")) {
            process = process.substring(8).trim();

            if (process.isEmpty()) {
                System.out.println(" OOPS!!! The description of a deadline cannot be empty.");
                return;
            }

            String[] parts = process.split(" /by ");

            if (parts.length < 2 || parts[1].trim().isEmpty()) {
                System.out.println(" OOPS!!! The description of an event cannot be empty.");
                return;
            }

            newT = new Deadline(parts[0], parts[1]);
        } else if (process.startsWith("event")) {
            process = process.substring(5).trim();
            if (process.isEmpty()) {
                System.out.println(" OOPS!!! The description of an event cannot be empty.");
                return;
            }

            String[] parts = process.split(" /from ");
            if (parts.length < 2) {
                System.out.println(" OOPS!!! The event must have a /from time.");
                return;
            }

            String descr = parts[0];
            String[] time = parts[1].split(" /to ");
            if (time.length < 2 || time[1].trim().isEmpty()) {
                System.out.println(" OOPS!!! The event must have a /to time.");
                return;
            }

            String from = time[0];
            String to = time.length > 1 ? time[1] : "";
            newT = new Event(descr, from, to);
        } else {
            System.out.println("Sorry I don't understand what you are saying ;-;");
            return;
        }

        current++;
        tasks.add(newT);
        System.out.println("Got it! I've added the task: " + newT);
        System.out.println("Currently you have " + (current + 1) + " tasks.");
    }

    public void list() {
        for (int i = 0; i <= current; i++) {
            System.out.println(
                    (i + 1) + ". " + tasks.get(i)
            );
        }
    }

    public void mark(String idx) {
        int i = Integer.parseInt(idx) - 1;
        tasks.get(i).mark();
        System.out.println(
                "Marked! \n" + tasks.get(i)
        );
    }

    public void unmark(String idx) {
        int i = Integer.parseInt(idx) - 1;
        tasks.get(i).unmark();
        System.out.println(
                "Unmarked! \n" + tasks.get(i)
        );
    }

    public void handle(String input) { //processes the inputs
        if (input.equals("list")) list();
        else if (input.startsWith("mark")) {
            this.mark(input.substring(5));
        }
        else if (input.startsWith("unmark")) {
            this.unmark(input.substring(7));
        }
        else this.add(input);
    }

    public static void main(String[] args) {
        Overflow bot = new Overflow();

        System.out.println(GREETINGS);
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();

        while (!input.equals("bye")) {
            bot.handle(input);
            input = sc.nextLine();
        }
        System.out.println(FAREWELL);
    }
}
