package shell.external;

import shell.state.ShellContext;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public class CommandExecutor {
    private CommandExecutor() {
        /* This utility class should not be instantiated */
    }

    public static void runExternal(String cmdName, List<String> args, ShellContext context) {
        try {
            String[] commandForProgram;
            commandForProgram = Stream.concat(
                    Stream.of(cmdName),
                    args.stream()
            ).toArray(String[]::new);
            ProcessBuilder pb = new ProcessBuilder(commandForProgram);

            pb.directory(context.getCurrentDirectory().toFile());
            pb.inheritIO();

            Process process = pb.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
                System.err.printf("Error executing %s: %s%n", cmdName, e.getMessage());
        }
    }
}
