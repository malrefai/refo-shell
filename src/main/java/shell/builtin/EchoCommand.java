package shell.builtin;

import shell.state.ShellContext;

import java.util.List;

public class EchoCommand implements Command {
    @Override
    public String execute(List<String> args, ShellContext context) {
        return String.join(" ", args);
    }
}
