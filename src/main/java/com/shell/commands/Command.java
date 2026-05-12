package com.shell.commands;

import com.shell.state.ShellContext;

import java.util.List;

public interface Command {
    // Every command must have an execute method
    String execute(List<String> args, ShellContext context);
}
