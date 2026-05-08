import java.io.File;
import java.util.*;
import java.util.function.Function;

public class Main {
    public static final Set<String> BUILTINS = Set.of("exit", "echo", "type");

    public static void main(String[] args) throws Exception {
         // Capture the user's command in the "command" variable
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("$ ");
            String input = scanner.nextLine().strip();

            String[] parts = input.split("\\s+", 2);
            String command = parts[0];
            String argument = (parts.length > 1) ? parts[1].strip() : "";

            if(command.equals("exit")) break;

            Map<String, Function<String, String>> actions = Map.of(
              "echo", cmd -> argument,
              "type", cmd -> evalType(argument)
            );

            System.out.println(actions.getOrDefault(command, "%s: command not found"::formatted).apply(command));
        }
    }

    public static String evalType(String command) {
        File executable;
        if (BUILTINS.contains(command)) {
            return String.format("%s is a shell builtin", command);
        } else if ((executable = findExecutable(command)) != null) {
            return String.format("%s is %s", command, executable);
        } else {
            return String.format("%s: not found", command);
        }
    }

    public static File findExecutable(String command) {
        String pathEnv = System.getenv("PATH");
        if (pathEnv == null) return null;

        String[] paths = pathEnv.split(File.pathSeparator);
        for(String path : paths) {
            File file = new File(path, command);
            if (file.isFile() && file.canExecute()) return file.getAbsoluteFile();
        }

        return null;
    }
}
