import java.util.Scanner;

public class Overflow {
    static String GREETINGS = "Good to see you!\nI'm Overflow, lemme know what I could do for you :>";
    static String FAREWELL = "Looking for the next time we meet!";
    private Task[] tasks = new Task[100];
    private int current = -1;

    public void add(input) {
        current++;
        tasks[i] = input;
    }

    public void list() {
        for (int i = 0; i <= current; i++) {
            System.out.println(
                    i + ". " + tasks[i]
            );
        }
    }

    public void handle(String input) { //processes the inputs
        if (input.equals("list")) list();
        else this.add(input);
    }

    public static void main(String[] args) {
        System.out.println(GREETINGS);
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        while (!input.equals("bye")) {
            System.out.println(input);
            input = sc.nextLine();
        }
        System.out.println(FAREWELL);
    }
}
