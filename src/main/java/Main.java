import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
         // Capture the user's command in the "command" variable
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("$ ");
            String input = scanner.nextLine().strip();

            String[] parts = input.split("\\s+", 2);
            String command = parts[0];
            String argument = (parts.length > 1) ? parts[1].strip() : "";

            if(command.equals("exit"))
                break;

            System.out.println(switch (command) {
                case "echo" -> argument;
                case "type" -> evalType(argument);
                default -> "%s: command not found%n".formatted(command);
            });
        }
    }

    public static String evalType(String command) {
        return switch (command) {
          case "exit", "echo", "type" -> String.format("%s is a shell builtin", command);
          default -> String.format("%s: not found", command);
        };
    }
}
