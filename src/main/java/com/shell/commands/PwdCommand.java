package com.shell.commands;

import com.shell.state.ShellContext;

import java.util.List;

public class PwdCommand implements Command {
    @Override
    public String execute(List<String> args, ShellContext context) {
        return context.getCurrentDirectory().toString();
    }
}
