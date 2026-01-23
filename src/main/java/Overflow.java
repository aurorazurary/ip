import java.util.Scanner;

public class Overflow {
    static String GREETINGS = "Good to see you!\nI'm Overflow, lemme know what I could do for you :>";
    static String FAREWELL = "Looking for the next time we meet!";
    private Task[] tasks = new Task[100];
    private int current = -1;

    public void add(String input) {
        current++;
        tasks[current] = new Task(input);
        System.out.println("added task: " + tasks[current]);
    }

    public void list() {
        for (int i = 0; i <= current; i++) {
            System.out.println(
                    (i + 1) + ". " + tasks[i]
            );
        }
    }

    public void handle(String input) { //processes the inputs
        if (input.equals("list")) list();
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
