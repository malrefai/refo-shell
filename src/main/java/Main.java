import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    public static final Set<String> BUILTINS = Set.of("exit", "echo", "pwd", "type");
    public static Path currentPath =  Paths.get("").toAbsolutePath();

    public static void main(String[] args) throws Exception {
         // Capture the user's command in the "command" variable
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("$ ");
            String input = scanner.nextLine().strip();

            String[] parts = input.split("\\s+");
            String command = parts[0];
            String[] arguments = switch(parts.length) {
                case int len when len > 1 -> Arrays.stream(parts).skip(1).toArray(String[]::new);
                default -> new String[0];
            };

            if (command.isBlank()) {
            } else if (command.equals("exit")) {
                break;
            } else if (BUILTINS.contains(command)) {
                handleBuiltin(command, arguments);
            } else if (findExecutable(command) != null) {
                execute(parts);
            } else {
                System.out.printf("%s: command not found%n", command);
            }
        }
    }

    public static void handleBuiltin(String command, String[] arguments) {
        Map<String, Function<String, String>> actions = Map.of(
                "echo", cmd -> String.join(" ", arguments),
                "pwd", cmd -> currentPath.toString(),
                "type", cmd -> Arrays.stream(arguments)
                                                .map(Main::evalType)
                                                .collect(Collectors.joining("\n"))
        );

        System.out.println( actions.getOrDefault(command, "%s: command not found"::formatted).apply(command));
    }

    public static File findExecutable(String command) {
        String pathEnv = System.getenv("PATH");
        if (pathEnv == null) return null;

        String[] paths = pathEnv.split(File.pathSeparator);
        for (String path : paths) {
            File file = new File(path, command);
            if (file.isFile() && file.canExecute()) return file.getAbsoluteFile();
        }

        return null;
    }

    public static String evalType(String command) {
        if (BUILTINS.contains(command)) {
            return String.format("%s is a shell builtin", command);
        } else {
            File executable;
            if ((executable = findExecutable(command)) != null) {
                return String.format("%s is %s", command, executable);
            } else {
                return String.format("%s: not found", command);
            }
        }
    }

    public static void execute(String[] fullCommand) throws Exception{
        ProcessBuilder pb = new ProcessBuilder(fullCommand);
        pb.inheritIO();

        Process process = pb.start();
        process.waitFor();
    }
}
