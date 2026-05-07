import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
         // Capture the user's command in the "command" variable
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.printf("$ ");
            String input = scanner.nextLine();

            if(input.equalsIgnoreCase("exit")) {
                break;
            } else if(input.startsWith("echo ")) {
                System.out.println(input.substring(5).strip());
            } else {
                System.out.printf("%s: command not found%n", input);
            }
        }
    }
}
