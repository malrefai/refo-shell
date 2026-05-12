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

        // Handle the Home Directory shortcut (~)
        if (targetStr.startsWith("~")) {
            String home = System.getenv("HOME");
            if (home == null) home = System.getProperty("user.home");

            targetStr = targetStr.replaceFirst("^~", home);
        }

        Path targetPath = Paths.get(targetStr);

        // Resolve Relative Paths (./, ../, or dir/)
        if (!targetPath.isAbsolute()) {
            targetPath = context.getCurrentDirectory().resolve(targetPath);
        }

        // Normalize the Path
        targetPath = targetPath.normalize().toAbsolutePath();

        // Validate and Update State
        if (Files.isDirectory(targetPath)) {
            context.setCurrentDirectory(targetPath);
            return "";
        } else {
            return "cd: %s: No such file or directory".formatted(targetStr);
        }
    }
}
