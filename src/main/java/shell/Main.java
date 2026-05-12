package shell;

import shell.builtin.BuiltinFactory;
import shell.external.CommandExecutor;
import shell.parser.CommandParser;
import shell.resolver.CommandResolution;
import shell.resolver.CommandResolver;
import shell.state.ShellContext;

import java.util.List;
import java.util.Scanner;

public class Main {

    static void main() {
        ShellContext context = new ShellContext();
        BuiltinFactory factory = new BuiltinFactory();
        Scanner scanner = new Scanner(System.in);

        while (context.isRunning()) {
            System.out.print("$ ");
            String input = scanner.nextLine().strip();
            if (input.isBlank()) continue;

            List<String> parts = CommandParser.parse(input);
            String cmdName = parts.getFirst();
            List<String> cmdArgs = parts.subList(1, parts.size());

            CommandResolution cmdResolution = CommandResolver.resolve(cmdName, context, factory.getNames());

            switch (cmdResolution.type()) {
                case BUILTIN -> {
                    String result = factory.getCommand(cmdName).execute(cmdArgs, context);
                    if (!result.isBlank()) System.out.println(result);
                }
                case EXTERNAL -> CommandExecutor.runExternal(cmdName, cmdArgs, context);
                case NOT_FOUND -> System.out.printf("%s: not found%n", cmdName);
            }

        }
    }
}
