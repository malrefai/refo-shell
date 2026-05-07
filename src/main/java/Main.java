import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
         // Capture the user's command in the "command" variable
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.printf("$ ");

            String command = scanner.nextLine();
            System.out.printf("%s: command not found%n", command);
        }
    }
}
