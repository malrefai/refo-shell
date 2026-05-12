package com.shell.execution;

import com.shell.state.ShellContext;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public class CommandExecutor {
    private CommandExecutor() {
        /* This utility class should not be instantiated */
    }


    public static void runExternal(String cmdName, List<String> args, ShellContext context) {
        File executable = findInPath(cmdName, context.getPaths());

        if (executable != null) {
            try {
                String[] fullCommand = Stream.concat(
                        Stream.of(executable.toString()),
                        args.stream()
                ).toArray(String[]::new);
                ProcessBuilder pb = new ProcessBuilder(fullCommand);

                pb.directory(context.getCurrentDirectory().toFile());
                pb.inheritIO();

                Process process = pb.start();
                process.waitFor();
            } catch (IOException | InterruptedException e) {
                System.err.printf("Error executing %s: %s%n", cmdName, e.getMessage());
            }
        } else {
            System.out.printf("%s: not found%n", cmdName);
        }
    }

    private static File findInPath(String cmdName, List<String> paths) {
        for (String path : paths) {
            File file = new File(path, cmdName);
            if (file.isFile() && file.canExecute()) return file;
        }
        return null;
    }
}
