import java.util.Scanner;

public class Overflow {
    static String GREETINGS = "Good to see you!\nI'm Overflow, lemme know what I could do for you :>";
    static String FAREWELL = "Looking for the next time we meet!";

    public static void main(String[] args) {
        System.out.println(GREETINGS);
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        while (!input.equals("bye")) {
            continue;
        }
        System.out.println(FAREWELL);
    }
}
