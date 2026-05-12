package com.shell.commands;

import com.shell.state.ShellContext;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CdCommand implements Command {
    @Override
    public String execute(List<String> args, ShellContext context) {
        if (args.isEmpty()) {
            return "";
        }

        String targetStr = args.getFirst();
        Path targetPath = Paths.get(targetStr);

        // Validate: Does it exist and is it a folder?
        if (Files.isDirectory(targetPath)) {
            context.setCurrentDirectory(targetPath);
            return "";
        } else {
            return "cd: %s: No such file or directory".formatted(targetStr);
        }
    }
}
