package com.shell;

import com.shell.commands.Command;
import com.shell.commands.CommandFactory;
import com.shell.execution.CommandExecutor;
import com.shell.parser.CommandParser;
import com.shell.state.ShellContext;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main() throws Exception {
        ShellContext context = new ShellContext();
        CommandFactory factory = new CommandFactory();
        Scanner scanner = new Scanner(System.in);

        while (context.isRunning()) {
            System.out.print("$ ");
            String input = scanner.nextLine().strip();
            if (input.isBlank()) continue;

            List<String> parts = CommandParser.parse(input);
            String cmdName = parts.getFirst();
            List<String> cmdArgs = parts.subList(1, parts.size());

            Command builtin = factory.getCommand(cmdName);

            if (builtin != null) {
                String result = builtin.execute(cmdArgs, context);
                if (!result.isBlank()) System.out.println(result);
            } else {
                CommandExecutor.runExternal(cmdName, cmdArgs, context);
            }
        }
    }
}
