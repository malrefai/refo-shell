import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
         System.out.printf("$ ");

         // Capture the user's command in the "command" variable
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();

        // Prints the "<command>: command not found" message
        System.out.printf("%s: command not found%n", command);
    }
}
