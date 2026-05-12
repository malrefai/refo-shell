package com.shell.commands;

import com.shell.state.ShellContext;

import java.util.List;

public class ExitCommand implements Command {
    @Override
    public String execute(List<String> args, ShellContext context) {
        context.stop();
        return "Goodbye!";
    }
}
