package com.shell.commands;


import com.shell.state.ShellContext;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record TypeCommand(Set<String> builtinNames) implements Command {

    @Override
    public String execute(List<String> args, ShellContext context) {
        return args.stream()
                .map(arg -> evaluate(arg, context.getPaths()))
                .collect(Collectors.joining("\n"));
    }

    private String evaluate(String cmdName, List<String> paths) {
        if (builtinNames.contains(cmdName)) return String.format("%s is a shell builtin", cmdName);

        for (String path : paths) {
            File file = new File(path, cmdName);
            if (file.isFile() && file.canExecute()) return String.format("%s is %s", cmdName, file.getAbsoluteFile());
        }

        return String.format("%s: not found", cmdName);
    }
}
